package cn.com.betacat.services.impl;

import cn.com.betacat.dao.BillMapper;
import cn.com.betacat.dao.PriceMapper;
import cn.com.betacat.dao.UsageMapper;
import cn.com.betacat.entity.Bill;
import cn.com.betacat.entity.Price;
import cn.com.betacat.entity.Usage;
import cn.com.betacat.services.BillService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillMapper billMapper;

    @Autowired
    private UsageMapper usageMapper;

    @Autowired
    private PriceMapper priceMapper;

    @Override
    public List<Usage> fillBill(List<Usage> usageList) {
        for (Usage usage : usageList) {
            usage.setBill(billMapper.selectByUsageId(usage.getId()));
        }
        return usageList;
    }

    @Override
    public Boolean generateBillBy(Bill templateBill) {
        List<Usage> usageList = usageMapper.selectList(null);
        for (Usage usage : usageList) {
            if (usage.getBill() != null) {
                continue;
            }

            Bill bill = new Bill();
            bill.setUsageId(usage.getId());
            bill.setAmount(usage.getAmount());
            bill.setDate(LocalDateTime.now());

            // 从模板账单中复制数据
            bill.setStatus(templateBill.getStatus());
            bill.setPayType(templateBill.getPayType());
            bill.setUnitPrice(templateBill.getUnitPrice());

            // 如果不存在宿舍，则不生成账单
            if (usage.getApartment() == null) {
                continue;
            }

            // 如果不存在学生，则不生成账单
            if (usage.getApartment().getStudents() == null) {
                continue;
            }

            bill.setSubsidy(templateBill.getSubsidy() * usage.getApartment().getStudents().size());

            // 计算总价，总价 = (用量 - 补贴) * 单价
            bill.setTotal((bill.getAmount() - bill.getSubsidy()) * bill.getUnitPrice());

            billMapper.insert(bill);
        }

        return true;
    }

    @SneakyThrows
    @Override
    public void generateBill(Integer userId) {
        List<Usage> usageList = usageMapper.selectAllUsagesAndApartmentAndStudentsAndBill();
        List<Price> priceList = priceMapper.selectList(null);
        Map<String, Double> unitPrice = new HashMap<>();
        Map<String, Double> subsidy = new HashMap<>();
        Integer percent = 0;
        Integer count = 0;
        Integer total = usageList.size();

        for (Price p : priceList) {
            unitPrice.put(p.getType(), p.getUnitPrice());
            subsidy.put(p.getType(), p.getSubsidy());
        }

        for (Usage usage : usageList) {

            // 计算百分比，发送报文
            count++;
            double percentDouble = (double) count / total * 100;
            // 如果百分比发生变化，则发送消息
            if ((int) percentDouble != percent) {
                percent = (int) percentDouble;
                if (percent > 100) {
                    percent = 100;
                }
                if (percent < 0) {
                    percent = 0;
                }
                sendMessage(percent, userId);
            }


            if (usage.getBill() != null) {
                continue;
            }

            Bill bill = new Bill();
            bill.setUsageId(usage.getId());
            bill.setAmount(usage.getAmount());
            bill.setDate(LocalDateTime.now());
            bill.setUnitPrice(unitPrice.get(usage.getType()));

            // 如果不存在宿舍，则不生成账单
            if (usage.getApartment() == null) {
                continue;
            }

            // 如果不存在学生，则不生成账单
            if (usage.getApartment().getStudents() == null) {
                continue;
            }

            bill.setSubsidy(subsidy.get(usage.getType()) * usage.getApartment().getStudents().size());

            // 计算总价，总价 = (用量 - 补贴) * 单价
            double totalPrice = (bill.getAmount() - bill.getSubsidy()) * bill.getUnitPrice();
            bill.setTotal(totalPrice < 0 ? 0 : totalPrice);

            billMapper.insert(bill);
        }
        sendMessage(100, userId);
    }


    /**
     * 自定义封装的发送方法
     * @param msg
     */
    private void sendMessage(Integer msg, Integer userId) {
        try {
            WebSocketServer.sendInfo(msg.toString(), userId.toString());
        } catch (IOException e) {
            log.error("消息发送异常：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
