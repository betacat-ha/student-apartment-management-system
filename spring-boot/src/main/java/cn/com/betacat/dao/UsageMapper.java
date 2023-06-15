package cn.com.betacat.dao;

import cn.com.betacat.entity.Apartment;
import cn.com.betacat.entity.Usage;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UsageMapper extends MPJBaseMapper<Usage> {
    @Deprecated
    @Select("select * from usage")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "apartmentId", column = "apartment_id"),
            @Result(property = "type", column = "type"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "status", column = "status"),
            @Result(property = "apartment", column = "apartment_id", javaType = Apartment.class,
                    one = @One(select = "cn.com.betacat.dao.ApartmentMapper.selectById"))
    })
    List<Usage> selectAllUsages();

    @Select("SELECT * FROM wae_usage WHERE apartment_id IN (SELECT id FROM apartment WHERE building_id = #{building_id})")
    List<Usage> getUsageByBuildingId(@Param("building_id") Integer buildingId);
}
