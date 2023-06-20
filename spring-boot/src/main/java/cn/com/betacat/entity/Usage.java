package cn.com.betacat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
