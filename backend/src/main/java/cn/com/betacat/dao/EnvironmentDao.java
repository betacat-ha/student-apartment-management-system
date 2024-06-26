package cn.com.betacat.dao;

import cn.com.betacat.pojo.Building;
import cn.com.betacat.pojo.Environment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EnvironmentDao extends BaseMapper<Environment> {

    @Select("select * from environment where apartment_id = #{apartmentId}")
    Environment selectByApartmentId(int apartmentId);
}
