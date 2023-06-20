package cn.com.betacat.controller;

import cn.com.betacat.entity.Result;
import cn.com.betacat.entity.User;
import cn.com.betacat.services.BillService;
import cn.com.betacat.services.UserService;
import cn.com.betacat.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillController {
    @Autowired
    private BillService billService;

    @Autowired
    private UserService userService;

    @GetMapping("/api/bill/generate")
    public Result generateBill(@RequestHeader("token") String token) {
        User user = userService.getUserInfoBy(token);
        billService.generateBill(user.getId());

        return Result.success("操作完成", null);
    }
}
