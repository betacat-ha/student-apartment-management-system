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
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "wae_usage")
public class Usage {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer apartmentId;
    @TableField(exist = false)
    private Integer buildingId;
    @TableField(exist = false)
    private String apartmentName;
    @TableField(exist = false)
    private Apartment apartment;
    @TableField(exist = false)
    private String buildingName;
    private String type;
    private Double amount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @TableField(exist = false)
    private String startTimeFormat;
    @TableField(exist = false)
    private String endTimeFormat;
    @TableField(exist = false)
    private Bill bill;
}
