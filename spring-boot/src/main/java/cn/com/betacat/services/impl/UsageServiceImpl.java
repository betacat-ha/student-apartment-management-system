package cn.com.betacat.services.impl;

import cn.com.betacat.dao.StudentMapper;
import cn.com.betacat.dao.UsageMapper;
import cn.com.betacat.entity.Student;
import cn.com.betacat.entity.Usage;
import cn.com.betacat.services.UsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsageServiceImpl implements UsageService {
    @Autowired
    private UsageMapper usageMapper;

    @Autowired
    private StudentMapper studentMapper;

    public List<Usage> getUsageWithApartmentsAndBillAndStudents() {
        List<Usage> usageList = usageMapper.selectAllUsages();

        // 为每个宿舍楼添加学生信息
        for (Usage usage : usageList) {
            if (usage.getApartment() == null) {
                continue;
            }
            usage.getApartment().setStudents(studentMapper.selectByApartmentId(usage.getApartment().getId()));
        }

        return usageList;
    }
}
