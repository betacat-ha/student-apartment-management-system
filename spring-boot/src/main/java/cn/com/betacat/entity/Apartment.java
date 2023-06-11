package cn.com.betacat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

@Data
public class Apartment {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer buildingId;
    private String name;
    @TableField(exist = false)
    private List<Student> students;
}
