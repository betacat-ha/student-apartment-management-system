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

import cn.com.betacat.dao.ApartmentMapper;
import cn.com.betacat.dao.StudentMapper;
import cn.com.betacat.entity.Apartment;
import cn.com.betacat.entity.Result;
import cn.com.betacat.entity.Student;
import cn.com.betacat.services.PermissionService;
import cn.com.betacat.util.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin // 允许跨域
@Slf4j
@RestController
public class StudentController {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ExcelUtil<Student> excelUtil;

    @GetMapping("/api/student/{id}")
    public Result query(@PathVariable String id, @RequestHeader String token) {
        Student student = studentMapper.selectStudentAndApartmentById(id);
        return new Result(200, "OK", student);
    }

    /**
     * 查询所有学生信息
     */
    @GetMapping("/api/student")
    public Result queryAll(@RequestHeader String token) {
        if (!permissionService.checkPermission(token, "STUDENT_QUERY")) {
            return Result.reject("你没有访问该资源的权限！");
        }
        List<Student> list = studentMapper.selectList(null);
        return new Result(200, "OK", list);
    }

    @GetMapping("/api/student/search")
    public Result queryBy(@RequestParam String content, @RequestParam String type, @RequestParam String gender) {
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        switch (type) {
            case "1": wrapper.like("id", content); break;
            case "2": wrapper.like("name", content); break;
            case "3": wrapper.like("class_name", content); break;
        }

        switch (gender) {
            case "1": wrapper.eq("gender", "男"); break;
            case "2": wrapper.eq("gender", "女"); break;
        }

        List<Student> list = studentMapper.selectList(wrapper);

        return new Result(200, "OK", list);
    }

    @GetMapping("/api/student/page")
    public Result query(int current, int size) {
        Page<Student> page = new Page<>(current, size);
        IPage<Student> iPage = studentMapper.selectPage(page,null);
        return new Result(200, "OK", iPage);
    }

    @PostMapping("/api/student")
    public Result update(@RequestBody Student student) {
        if (student.getId() == null || student.getId().isEmpty() ||
                student.getName() == null || student.getName().isEmpty() ||
                student.getGender() == null ||
                !(student.getGender().equals("男") || student.getGender().equals("女"))) {
            return new Result(400, "服务器开小差了，请刷新后重试", null);
        }

        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("id", student.getId());

        List<Student> list = studentMapper.selectList(wrapper);
        if (list.size() != 0) {
            studentMapper.update(student, wrapper);
            return new Result(200, "更新学生数据成功", null);
        }

        studentMapper.insert(student);
        return new Result(200, "添加学生数据成功", null);
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

    @GetMapping("/api/student/export")
    public ResponseEntity<Resource> exportStudent(HttpServletResponse response) {
        List<Student> studentList = studentMapper.selectList(null);
        String filePath = excelUtil.write("学生数据", studentList, Student.class);
        log.info("获取到生成的Excel文件：" + filePath);

        // 获取文件资源
        Resource resource = new FileSystemResource(filePath);

        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filePath);

        // 返回文件资源的ResponseEntity
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
