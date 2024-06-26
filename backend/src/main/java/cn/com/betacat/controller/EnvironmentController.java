package cn.com.betacat.controller;

import cn.com.betacat.pojo.Environment;
import cn.com.betacat.pojo.Result;
import cn.com.betacat.pojo.Student;
import cn.com.betacat.pojo.Usage;
import cn.com.betacat.services.EnvironmentService;
import cn.com.betacat.services.StudentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/mobile/environment")
public class EnvironmentController {
    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/get")
    public Result get(@RequestHeader String token){
        Student student = studentService.getStudentInfoBy(token);

        if (student.getApartmentId() == null && student.getApartmentId().equals(0)) {
            return Result.error("该生没有分配宿舍号！");
        }

        Environment environment = environmentService.getInfo(student.getApartmentId());

        return Result.success(environment);
    }

    @PostMapping("/update")
    public Result update(@RequestBody Environment newEnvironment, @RequestHeader String token){
        Student student = studentService.getStudentInfoBy(token);

        if (student.getApartmentId() == null && student.getApartmentId().equals(0)) {
            return Result.error("该生没有分配宿舍号！");
        }

        return environmentService.updateInfo(student.getApartmentId(), newEnvironment) ?
                Result.success("数据修改成功") :
                Result.error(500, "系统内部错误");
    }
}
