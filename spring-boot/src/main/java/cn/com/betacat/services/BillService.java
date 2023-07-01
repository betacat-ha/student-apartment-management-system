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
