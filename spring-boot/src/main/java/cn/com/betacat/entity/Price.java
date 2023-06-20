package cn.com.betacat.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Price {
    @TableId
    Integer id;
    String type;
    Double subsidy;
    Double unitPrice;
}
