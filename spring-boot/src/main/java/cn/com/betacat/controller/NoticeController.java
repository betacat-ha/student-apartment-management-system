package cn.com.betacat.controller;

import cn.com.betacat.entity.Result;
import cn.com.betacat.services.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin // 允许跨域
@RestController
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/api/notice/all")
    public Result getAllNotice() {
        return Result.success(noticeService.getAllNotice());
    }

}

