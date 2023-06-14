package cn.com.betacat.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class Student {
    private String id;
    private Integer apartmentId;
    @TableField(exist = false)
    @JsonIgnore
    private Apartment apartment;
    private String className;
    private String name;
    private Integer age;
    private String gender;
    private String phone;
    private String email;
    private String status;
}
