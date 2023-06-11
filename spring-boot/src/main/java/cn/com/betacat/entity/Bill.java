package cn.com.betacat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Bill {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer usageId;
    private Double amount;
    private String status;
    private LocalDateTime date;
    private LocalDateTime payDate;
    private String payType;
}
