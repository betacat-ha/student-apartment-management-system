package cn.com.betacat.services.impl;

import cn.com.betacat.dao.NoticeMapper;
import cn.com.betacat.entity.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl {
    @Autowired
    private NoticeMapper noticeMapper;
    public List<Notice> getAllNotice() {
        return noticeMapper.selectList(null);
    }
}
