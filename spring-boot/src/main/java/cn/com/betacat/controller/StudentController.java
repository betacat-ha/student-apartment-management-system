package cn.com.betacat.controller;

import cn.com.betacat.dao.ApartmentMapper;
import cn.com.betacat.dao.StudentMapper;
import cn.com.betacat.entity.Apartment;
import cn.com.betacat.entity.Result;
import cn.com.betacat.entity.Student;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin // 允许跨域
@RestController
public class StudentController {
    @Autowired
    private StudentMapper studentMapper;

    @GetMapping("/student/{id}")
    public Result query(@PathVariable String id) {
        Student student = studentMapper.selectStudentAndApartmentById(id);
        return new Result(200, "OK", student);
    }

    /**
     * 查询所有学生信息
     */
    @GetMapping("/student")
    public Result query() {
        List<Student> list = studentMapper.selectList(null);
        return new Result(200, "OK", list);
    }

    @GetMapping("/student/page")
    public Result query(int current, int size) {
        Page<Student> page = new Page<>(current, size);
        IPage<Student> iPage = studentMapper.selectPage(page,null);
        return new Result(200, "OK", iPage);
    }
}
