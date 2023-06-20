package cn.com.betacat.dao;

import cn.com.betacat.entity.Bill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BillMapper extends BaseMapper<Bill> {

        /**
        * 根据用量id查询账单
        */
        @Select("select * from bill where usage_id = #{usageId}")
        Bill selectByUsageId(Integer usageId);
}
