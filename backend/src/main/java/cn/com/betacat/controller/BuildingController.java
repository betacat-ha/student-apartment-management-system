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

import cn.com.betacat.dao.ApartmentDao;
import cn.com.betacat.dao.BuildingDao;
import cn.com.betacat.pojo.Apartment;
import cn.com.betacat.pojo.Building;
import cn.com.betacat.pojo.Result;
import cn.com.betacat.services.PermissionService;
import cn.com.betacat.services.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class BuildingController {
    @Autowired
    private BuildingDao buildingDao;

    @Autowired
    private ApartmentDao apartmentDao;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserService userService;

    @GetMapping("/building")
    public Result query(@RequestHeader String token) {
        if (!permissionService.checkPermission(token, "BUILDING_QUERY")) {
            return Result.reject("你没有访问该资源的权限！");
        }

        List<Building> list = buildingDao.selectAllBuildingsAndApartments();
        return new Result(200, "OK", list);
    }

    @PostMapping("/building")
    public Result update(@RequestBody Building building, @RequestHeader String token) {
        if (!permissionService.checkPermission(token, "BUILDING_UPDATE")) {
            return Result.reject("你没有访问该资源的权限！");
        }

        //buildingMapper.insert(building);
        boolean haveError = false;

        if (building.getApartments() == null ||
                building.getName() == null || building.getName().equals("")
        ) {
            return new Result(400, "请检查填写的数据", null);
        }

        if (building.getId() == null || building.getId().equals(0)) {
            buildingDao.insert(building);
        } else {
            buildingDao.updateById(building);
        }

        // 分别更新每个房间数据
        for (Apartment apartment : building.getApartments()) {
            if (apartment.getName() == null || apartment.getName().equals("")) {
                haveError = true;
                break;
            }
            apartment.setBuildingId(building.getId());

            if (apartment.getId() == null || apartment.getId().equals(0)) {
                apartmentDao.insert(apartment);
            } else {
                apartmentDao.updateById(apartment);
            }
        }

        // MARK: 删除多余的房间模块

        // 获取数据库中已有的房间
        QueryWrapper<Apartment> wrapper = new QueryWrapper<>();
        wrapper.eq("building_id", building.getId());
        List<Apartment> existingApartments = apartmentDao.selectList(wrapper);

        // 获取所有房间的id
        List<Integer> apartmentIds = new ArrayList<>();
        for (Apartment apartment : building.getApartments()) {
            apartmentIds.add(apartment.getId());
        }

        // 删除多余的房间
        for (Apartment existingApartment : existingApartments) {
            if (!apartmentIds.contains(existingApartment.getId())) {
                apartmentDao.deleteById(existingApartment.getId());
            }
        }

        // 更新管理员
        for (Integer id : building.getAdministratorId()) {
            userService.updateBuildingAdminBy(id, building.getId());
        }

        return haveError ?
                new Result(400, "部分数据未保存，请仔细检查！", null) :
                new Result(200, "OK", null);
    }

    @DeleteMapping("/building")
    public Result delete(Integer id, @RequestHeader String token) {
        if (!permissionService.checkPermission(token, "BUILDING_UPDATE")) {
            return Result.reject("你没有访问该资源的权限！");
        }

        buildingDao.deleteById(id);
        return new Result(200, "OK", null);
    }
}
