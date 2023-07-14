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

package cn.com.betacat.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
//import org.springframework.security.core.GrantedAuthority;

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
    private Integer buildingId;

//    /**
//     * 权限列表 */
//    @TableField(exist = false)
//    Collection<? extends GrantedAuthority> authorities;
}
