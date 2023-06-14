package cn.com.betacat.controller;

import cn.com.betacat.dao.ApartmentMapper;
import cn.com.betacat.dao.StudentMapper;
import cn.com.betacat.entity.Apartment;
import cn.com.betacat.entity.Result;
import cn.com.betacat.entity.Student;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin // 允许跨域
@RestController
public class StudentController {
    @Autowired
    private StudentMapper studentMapper;

    @GetMapping("/api/student/{id}")
    public Result query(@PathVariable String id) {
        Student student = studentMapper.selectStudentAndApartmentById(id);
        return new Result(200, "OK", student);
    }

    /**
     * 查询所有学生信息
     */
    @GetMapping("/api/student")
    public Result query() {
        List<Student> list = studentMapper.selectList(null);
        return new Result(200, "OK", list);
    }

    @GetMapping("/api/student/search")
    public Result queryBy(@RequestParam String content, @RequestParam String type, @RequestParam String gender) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        int queryConditionAmount = 2;
        switch (type) {
            case "1": wrapper.like("id", content); break;
            case "2": wrapper.like("name", content); break;
            case "3": wrapper.like("class_name", content); break;
            default: queryConditionAmount--;
        }

        switch (gender) {
            case "1": wrapper.eq("gender", "男"); break;
            case "2": wrapper.eq("gender", "女"); break;
            default: queryConditionAmount--;
        }

        List<Student> list = studentMapper.selectList(
                queryConditionAmount == 0 ? null : wrapper
        );

        return new Result(200, "OK", list);
    }

    @GetMapping("/api/student/page")
    public Result query(int current, int size) {
        Page<Student> page = new Page<>(current, size);
        IPage<Student> iPage = studentMapper.selectPage(page,null);
        return new Result(200, "OK", iPage);
    }

    @DeleteMapping("/api/student")
    public  Result deleteById(String id){
        int i = studentMapper.deleteById(id);
        if (i > 0) {
            return new Result(200, "OK", null);
        } else {
            return new Result(500, "系统内部错误", null);
        }
    }
}
