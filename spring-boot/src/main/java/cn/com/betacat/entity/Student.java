package cn.com.betacat.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Student {
    private String id;
    private Integer apartmentId;
    @TableField(exist = false)
    private Apartment apartment;
    private String className;
    private String name;
    private Integer age;
    private String sex;
    private String phone;
    private String email;
    private String status;
}
