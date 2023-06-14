package cn.com.betacat.controller;

import cn.com.betacat.dao.ApartmentMapper;
import cn.com.betacat.entity.Apartment;
import cn.com.betacat.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class ApartmentController {
    @Autowired
    private ApartmentMapper apartmentMapper;

    @GetMapping("/api/apartment/with-students")
    public Result queryWithStudents() {
        List<Apartment> list = apartmentMapper.selectAllApartmentsAndStudents();
        return new Result(200, "OK", list);
    }

    @GetMapping("/api/apartment")
    public Result query() {
        List<Apartment> list = apartmentMapper.selectList(null);
        return new Result(200, "OK", list);
    }
}
