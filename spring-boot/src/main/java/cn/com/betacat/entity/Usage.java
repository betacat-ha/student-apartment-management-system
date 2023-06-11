package cn.com.betacat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Usage {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer apartmentId;
    private String type;
    private Double amount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
