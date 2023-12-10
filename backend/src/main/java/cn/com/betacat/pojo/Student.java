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

import com.alibaba.excel.annotation.*;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
