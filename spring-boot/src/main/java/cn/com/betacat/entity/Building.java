package cn.com.betacat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

@Data
public class Building {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    @TableField(exist = false)
    private List<Apartment> apartments;

    @TableField(exist = false)
    private List<Integer> administratorId;
}
