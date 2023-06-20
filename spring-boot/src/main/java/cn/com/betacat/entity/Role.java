package cn.com.betacat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Role {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String status;
    private String description;
    private String permission;
}
