package cn.com.betacat.dao;

import cn.com.betacat.entity.Price;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PriceMapper extends BaseMapper<Price> {
    @Select("select * from price where type = #{type}")
    Price selectByType(String type);
}
