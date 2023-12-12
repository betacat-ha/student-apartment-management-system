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

package cn.com.betacat.controller;

import cn.com.betacat.pojo.Result;
import cn.com.betacat.pojo.User;
import cn.com.betacat.services.BillService;
import cn.com.betacat.services.UsageService;
import cn.com.betacat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private BillService billService;

    @Autowired
    private UsageService usageService;

    @Autowired
    private UserService userService;

    /**
     * 批量生成账单
     * @param token 识别操作用户，建立连接
     * @return 操作结果
     */
    @GetMapping("/generate")
    public Result generateBill(@RequestHeader("token") String token) {
        User user = userService.getUserInfoBy(token);
        billService.generateBill(user.getId());

        return Result.success("操作完成", null);
    }

    /**
     * 查询账单
     * @return 操作结果
     */
    @GetMapping("/query")
    public Result queryBill() {
        return Result.success("操作完成", billService.fillBill(usageService.getUsageList()));
    }
}
