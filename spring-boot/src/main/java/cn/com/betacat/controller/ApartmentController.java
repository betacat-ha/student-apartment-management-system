package cn.com.betacat.controller;

import cn.com.betacat.dao.ApartmentMapper;
import cn.com.betacat.entity.Apartment;
import cn.com.betacat.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApartmentController {
    @Autowired
    private ApartmentMapper apartmentMapper;

    @GetMapping("/apartment")
    public Result query() {
        List<Apartment> list = apartmentMapper.selectAllApartmentsAndStudents();
        return new Result(200, "OK", list);
    }
}
