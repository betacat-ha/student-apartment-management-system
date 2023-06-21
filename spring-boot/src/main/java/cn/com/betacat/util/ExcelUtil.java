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
