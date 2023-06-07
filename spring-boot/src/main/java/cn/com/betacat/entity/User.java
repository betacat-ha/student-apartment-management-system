package cn.com.betacat.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private Integer roleId;
    private String name;
    private String nickname;
    private String password;
    private String phone;
    private String email;
}
