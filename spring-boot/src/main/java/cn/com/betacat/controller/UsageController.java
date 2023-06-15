package cn.com.betacat.controller;

import cn.com.betacat.dao.BuildingMapper;
import cn.com.betacat.dao.UsageMapper;
import cn.com.betacat.entity.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@CrossOrigin
@RestController
public class UsageController {
    @Autowired
    private UsageMapper usageMapper;

    @Autowired
    private BuildingMapper buildingMapper;

    public List<Usage> outputFormat(List<Usage> list) {
        List<Building> buildingList = buildingMapper.selectAllBuildingsAndApartments();
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

    @GetMapping("/api/usage")
    public Result query() {
        List<Usage> list = usageMapper.selectList(null);
        list = outputFormat(list);
        return new Result(200, "OK", list);
    }

    @GetMapping("/api/usage/search")
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
        List<Usage> list = usageMapper.selectList(wrapper);
        list = outputFormat(list);
        return new Result(200, "OK", list);
    }

    @PostMapping("/api/usage")
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

        if (usage.getId() == null || usage.getId().equals(0)) {
            return usageMapper.insert(usage) == 1 ?
                    new Result(200, "数据添加成功", null) :
                    new Result(500, "系统内部错误", null);
        }

        QueryWrapper<Usage> wrapper = new QueryWrapper<>();
        wrapper.eq("id", usage.getId());

        usage.setStartTime(LocalDateTimeFormat(usage.getStartTimeFormat()));
        usage.setEndTime(LocalDateTimeFormat(usage.getEndTimeFormat()));

        return usageMapper.update(usage, wrapper) == 1 ?
                new Result(200, "数据修改成功", null) :
                new Result(500, "系统内部错误", null);
    }
}
