package cn.com.betacat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Building {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
}
