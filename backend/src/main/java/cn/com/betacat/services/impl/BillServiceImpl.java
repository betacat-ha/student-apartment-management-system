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

package cn.com.betacat.services.impl;

import cn.com.betacat.dao.BillDao;
import cn.com.betacat.dao.PriceDao;
import cn.com.betacat.dao.UsageDao;
import cn.com.betacat.pojo.Bill;
import cn.com.betacat.pojo.Price;
import cn.com.betacat.pojo.Usage;
import cn.com.betacat.services.BillService;
import cn.com.betacat.util.WebSocketServer;
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
    private BillDao billDao;

    @Autowired
    private UsageDao usageDao;

    @Autowired
    private PriceDao priceDao;

    @Override
    public List<Usage> fillBill(List<Usage> usageList) {
        for (Usage usage : usageList) {
            usage.setBill(billDao.selectByUsageId(usage.getId()));
        }
        return usageList;
    }

    @Override
    public Boolean generateBillBy(Bill templateBill) {
        List<Usage> usageList = usageDao.selectList(null);
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

            billDao.insert(bill);
        }

        return true;
    }

    @SneakyThrows
    @Override
    public void generateBill(Integer userId) {
        List<Usage> usageList = usageDao.selectAllUsagesAndApartmentAndStudentsAndBill();
        List<Price> priceList = priceDao.selectList(null);
        Map<String, Double> unitPrice = new HashMap<>();
        Map<String, Double> subsidy = new HashMap<>();
        Integer percent = 0;
        Integer count = 0;
        Integer total = usageList.size();

        log.info("开始生成账单");

        for (Price p : priceList) {
            unitPrice.put(p.getType(), p.getUnitPrice());
            subsidy.put(p.getType(), p.getSubsidy());
        }

        for (Usage usage : usageList) {
            // 减缓运行速度
            Thread.sleep(100);

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

            billDao.insert(bill);
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
