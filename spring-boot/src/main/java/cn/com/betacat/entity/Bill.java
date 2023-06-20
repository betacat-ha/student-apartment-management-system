package cn.com.betacat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Bill {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer usageId;
    private Double amount;          // 用电量/用水量
    private String status;          // 支付状态，0未支付，1已支付
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime date;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime payDate;
    private String payType;         // 支付方式，0支付宝，1微信，2银行卡
    private Double unitPrice;       // 单价
    private Double subsidy;         // 补贴（单位：度/立方）
    private Double total;           // 总价
}
