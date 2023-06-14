package cn.com.betacat.controller;

import cn.com.betacat.dao.BuildingMapper;
import cn.com.betacat.entity.Building;
import cn.com.betacat.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class BuildingController {
    @Autowired
    private BuildingMapper buildingMapper;


    @GetMapping("/api/building")
    public Result query() {
        List<Building> list = buildingMapper.selectAllBuildingsAndApartments();
        return new Result(200, "OK", list);
    }
}
