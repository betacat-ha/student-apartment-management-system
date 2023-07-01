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

package cn.com.betacat.util;

import com.alibaba.excel.EasyExcel;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class ExcelUtil<T> {
    /**
     * 写入文件
     * @param name 文件名
     * @param data 数据
     * @return 文件路径
     */
    public String write(String name, List<T> data, Class Head) {
        String fileName = FileUtil.getPath() + name + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, Head)
                .sheet("模板")
                .doWrite(() -> {
                    // 分页查询数据
                    return data;
                });
        return fileName;
    }
}
