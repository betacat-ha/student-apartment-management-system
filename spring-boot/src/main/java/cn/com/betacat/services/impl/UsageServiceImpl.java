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

import cn.com.betacat.dao.StudentMapper;
import cn.com.betacat.dao.UsageMapper;
import cn.com.betacat.pojo.Usage;
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
