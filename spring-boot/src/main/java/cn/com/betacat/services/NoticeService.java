package cn.com.betacat.services;


import cn.com.betacat.entity.Notice;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface NoticeService {
    /**
     * 获取所有公告
     * @return 公告列表
     */
    List<Notice> getAllNotice();
}
