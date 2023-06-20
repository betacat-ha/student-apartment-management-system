package cn.com.betacat.controller;

import cn.com.betacat.dao.ApartmentMapper;
import cn.com.betacat.dao.BuildingMapper;
import cn.com.betacat.entity.Apartment;
import cn.com.betacat.entity.Building;
import cn.com.betacat.entity.Result;
import cn.com.betacat.entity.Usage;
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
    private BuildingMapper buildingMapper;

    @Autowired
    private ApartmentMapper apartmentMapper;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserService userService;

    @GetMapping("/api/building")
    public Result query(@RequestHeader String token) {
        if (!permissionService.checkPermission(token, "BUILDING_QUERY")) {
            return Result.reject("你没有访问该资源的权限！");
        }

        List<Building> list = buildingMapper.selectAllBuildingsAndApartments();
        return new Result(200, "OK", list);
    }

    @PostMapping("/api/building")
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
            buildingMapper.insert(building);
        } else {
            buildingMapper.updateById(building);
        }

        // 分别更新每个房间数据
        for (Apartment apartment : building.getApartments()) {
            if (apartment.getName() == null || apartment.getName().equals("")) {
                haveError = true;
                break;
            }
            apartment.setBuildingId(building.getId());

            if (apartment.getId() == null || apartment.getId().equals(0)) {
                apartmentMapper.insert(apartment);
            } else {
                apartmentMapper.updateById(apartment);
            }
        }

        // MARK: 删除多余的房间模块

        // 获取数据库中已有的房间
        QueryWrapper<Apartment> wrapper = new QueryWrapper<>();
        wrapper.eq("building_id", building.getId());
        List<Apartment> existingApartments = apartmentMapper.selectList(wrapper);

        // 获取所有房间的id
        List<Integer> apartmentIds = new ArrayList<>();
        for (Apartment apartment : building.getApartments()) {
            apartmentIds.add(apartment.getId());
        }

        // 删除多余的房间
        for (Apartment existingApartment : existingApartments) {
            if (!apartmentIds.contains(existingApartment.getId())) {
                apartmentMapper.deleteById(existingApartment.getId());
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

    @DeleteMapping("/api/building")
    public Result delete(Integer id, @RequestHeader String token) {
        if (!permissionService.checkPermission(token, "BUILDING_UPDATE")) {
            return Result.reject("你没有访问该资源的权限！");
        }

        buildingMapper.deleteById(id);
        return new Result(200, "OK", null);
    }
}
