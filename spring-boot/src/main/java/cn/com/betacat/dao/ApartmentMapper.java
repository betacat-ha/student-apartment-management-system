package cn.com.betacat.dao;

import cn.com.betacat.entity.Apartment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ApartmentMapper extends BaseMapper<Apartment> {

    @Select("select * from apartment")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "building_id", property = "buildingId"),
            @Result(column = "name", property = "name"),
            @Result(column = "id", property = "students", javaType = List.class,
                    many = @Many(select = "cn.com.betacat.dao.StudentMapper.selectByApartmentId"))
    })
    List<Apartment> selectAllApartmentsAndStudents();

    @Select("select * from apartment where id = #{apartmentId}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "building_id", property = "buildingId"),
            @Result(column = "name", property = "name"),
            @Result(column = "id", property = "students", javaType = List.class,
                    many = @Many(select = "cn.com.betacat.dao.StudentMapper.selectByApartmentId"))
    })
    List<Apartment> selectApartmentAndStudentsById(Integer apartmentId);

    @Select("select * from apartment where building_id = #{buildingId}")
    List<Apartment> selectByBuildingId(Integer buildingId);

}
