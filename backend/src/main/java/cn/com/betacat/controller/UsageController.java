/*
 * Copyright 2023 BetaCat_HA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.com.betacat.controller;

import cn.com.betacat.dao.BuildingDao;
import cn.com.betacat.dao.UsageDao;
import cn.com.betacat.pojo.*;
import cn.com.betacat.services.BillService;
import cn.com.betacat.services.StudentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class UsageController {
    @Autowired
    private UsageDao usageDao;

    @Autowired
    private BuildingDao buildingDao;

    @Autowired
    private BillService billService;

    @Autowired
    private StudentService studentService;


    public List<Usage> outputFormat(List<Usage> list) {
        List<Building> buildingList = buildingDao.selectAllBuildingsAndApartments();
        Map<Integer,String> buildingNameMap = new HashMap<>();
        Map<Integer,String> apartmentNameMap = new HashMap<>();

        for(Building building:buildingList){
            for (Apartment apartment: building.getApartments()){
                apartmentNameMap.put(apartment.getId(),apartment.getName());
                buildingNameMap.put(apartment.getId(),building.getName());
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for(Usage usage:list){
            usage.setApartmentName(apartmentNameMap.get(usage.getApartmentId()));
            usage.setBuildingName(buildingNameMap.get(usage.getApartmentId()));

            LocalDateTime dateTime = usage.getStartTime();
            String timeFormat = dateTime.format(formatter);
            usage.setStartTimeFormat(timeFormat);

            dateTime = usage.getEndTime();
            timeFormat = dateTime.format(formatter);
            usage.setEndTimeFormat(timeFormat);

        }

        return list;
    }

    public LocalDateTime LocalDateTimeFormat(String dateTimeStr){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTimeStr, formatter);
    }

    @GetMapping("/usage")
    public Result query() {
        List<Usage> list = usageDao.selectList(null);
        list = outputFormat(list);
        list = billService.fillBill(list);
        return new Result(200, "OK", list);
    }

    @GetMapping("/usage/search")
    public Result queryBy(Usage usage) {
        QueryWrapper<Usage> wrapper = new QueryWrapper<>();

        if (usage.getType() != null && (usage.getType().equals("水") || usage.getType().equals("电"))) {
            wrapper.eq("type", usage.getType());
        }

        if (usage.getApartmentId() != null && !usage.getApartmentId().equals(0)) {
            wrapper.eq("apartment_id", usage.getApartmentId());
        } else if (usage.getBuildingId() != null && !usage.getBuildingId().equals(0)) {
            String subQuery = "(SELECT id FROM apartment WHERE building_id = " + usage.getBuildingId() + ")";
            wrapper.inSql("apartment_id", subQuery);
        }
        List<Usage> list = usageDao.selectList(wrapper);
        list = outputFormat(list);

        list = billService.fillBill(list);

        return new Result(200, "OK", list);
    }

    @PostMapping("/usage")
    public Result update(@RequestBody Usage usage) {
        if (usage.getApartmentId() == null || usage.getApartmentId().equals(0) ||
                usage.getType() == null ||
                !(usage.getType().equals("水") || usage.getType().equals("电")) ||
                usage.getAmount() == null || usage.getAmount().equals(0.0) ||
                usage.getStartTimeFormat() == null || usage.getStartTimeFormat().equals("") ||
                usage.getEndTimeFormat() == null || usage.getEndTimeFormat().equals("")
        ) {
            return new Result(400, "数据填写不完整", null);
        }

        // 日期格式转换
        usage.setStartTime(LocalDateTimeFormat(usage.getStartTimeFormat()));
        usage.setEndTime(LocalDateTimeFormat(usage.getEndTimeFormat()));

        if (usage.getId() == null || usage.getId().equals(0)) {
            return usageDao.insert(usage) == 1 ?
                    new Result(200, "数据添加成功", null) :
                    new Result(500, "系统内部错误", null);
        }

        QueryWrapper<Usage> wrapper = new QueryWrapper<>();
        wrapper.eq("id", usage.getId());

        return usageDao.update(usage, wrapper) == 1 ?
                new Result(200, "数据修改成功", null) :
                new Result(500, "系统内部错误", null);
    }

    @DeleteMapping("/usage")
    public Result delete(Integer id) {
        if (id == null || id.equals(0)) {
            return new Result(400, "请刷新页面后再试！", null);
        }

        QueryWrapper<Usage> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);

        return usageDao.delete(wrapper) >= 1 ?
                new Result(200, "数据删除成功", null) :
                new Result(500, "系统内部错误", null);
    }

    @GetMapping("/mobile/usage/{type}")
    public Result mobileQuery(@PathVariable String type, @RequestHeader String token) {
        Student student = studentService.getStudentInfoBy(token);

        QueryWrapper<Usage> wrapper = new QueryWrapper<>();

        if (student.getApartmentId() == null && student.getApartmentId().equals(0)) {
            return Result.error("该生没有分配宿舍号！");
        }
        wrapper.eq("apartment_id", student.getApartmentId());

        switch (type) {
            case "water":
                type = "水";
                break;
            case "electricity" :
                type = "电";
                break;
            default:
                return Result.error("非法请求");
        }
        wrapper.eq("type", type);

        List<Usage> list = usageDao.selectList(wrapper);
        list = outputFormat(list);

        list = billService.fillBill(list);

        return new Result(200, "OK", list);
    }
}
