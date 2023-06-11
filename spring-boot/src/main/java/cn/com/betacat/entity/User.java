package cn.com.betacat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer roleId;
    private String name;
    private String nickname;
    private String password;
    private String phone;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime lastLoginTime;
    private String status;
}
