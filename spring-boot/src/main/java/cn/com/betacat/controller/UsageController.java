package cn.com.betacat.controller;

import cn.com.betacat.dao.BuildingMapper;
import cn.com.betacat.dao.UsageMapper;
import cn.com.betacat.entity.Apartment;
import cn.com.betacat.entity.Building;
import cn.com.betacat.entity.Result;
import cn.com.betacat.entity.Usage;
import org.apache.ibatis.annotations.Results;
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

    @GetMapping("/api/usage")
    public Result query() {
        List<Building> buildingList = buildingMapper.selectAllBuildingsAndApartments();
        Map<Integer,String> buildingNameMap = new HashMap<>();
        Map<Integer,String> apartmentNameMap = new HashMap<>();

        for(Building building:buildingList){
            for (Apartment apartment: building.getApartments()){
                apartmentNameMap.put(apartment.getId(),apartment.getName());
                buildingNameMap.put(apartment.getId(),building.getName());
            }
        }

        List<Usage> list = usageMapper.selectList(null);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a", Locale.US);

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

        return new Result(200, "OK", list);
    }


}
