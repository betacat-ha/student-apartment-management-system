package cn.com.betacat.entity;

import com.alibaba.excel.annotation.*;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class Student {
    @ExcelProperty("学号")
    private String id;

    @TableField(exist = false)
    @JsonIgnore
    @ExcelIgnore
    private Apartment apartment;

    @TableField(exist = false)
    @ExcelProperty("楼栋号")
    private String buildingName;

    @TableField(exist = false)
    @ExcelProperty("宿舍号")
    private String apartmentName;

    @ExcelProperty("宿舍id")
    private Integer apartmentId;
    @ExcelProperty("班级")
    private String className;
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("年龄")
    private Integer age;
    @ExcelProperty("性别")
    private String gender;
    @ExcelProperty("电话")
    private String phone;
    @ExcelProperty("邮箱")
    private String email;
    @ExcelProperty("状态")
    private String status;
}
