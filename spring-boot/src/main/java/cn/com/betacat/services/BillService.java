package cn.com.betacat.services;

import cn.com.betacat.entity.Bill;
import cn.com.betacat.entity.Usage;

import java.util.List;

public interface BillService {
    /**
     * 根据用量id查询账单，并填充回用量对象
     * @param usageList 用量列表
     * @return 账单列表
     */
    List<Usage> fillBill(List<Usage> usageList);

    /**
     * 根据模板账单生成账单
     * @param templateBill 模板账单
     * @return 是否成功
     */
    Boolean generateBillBy(Bill templateBill);

    /**
     * 生成账单
     */
    void generateBill(Integer userId);
}
