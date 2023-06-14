package cn.com.betacat.dao;

import cn.com.betacat.entity.Building;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BuildingMapper extends BaseMapper<Building> {
    @Select("select * from building")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "id", property = "apartments", javaType = List.class,
                    many = @Many(select = "cn.com.betacat.dao.ApartmentMapper.selectByBuildingId"))
    })
    List<Building> selectAllBuildingsAndApartments();
}
