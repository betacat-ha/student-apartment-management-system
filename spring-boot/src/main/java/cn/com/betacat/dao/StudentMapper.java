package cn.com.betacat.dao;

import cn.com.betacat.entity.Apartment;
import cn.com.betacat.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    @Select("select * from student where apartment_id = #{apartmentId}")
    List<Student> selectByApartmentId(Integer apartmentId);


    @Select("select * from student where id = #{id}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "apartment_id", property = "apartmentId"),
            @Result(column = "class_name", property = "className"),
            @Result(column = "name", property = "name"),
            @Result(column = "age", property = "age"),
            @Result(column = "sex",property = "sex"),
            @Result(column = "phone",property = "phone"),
            @Result(column = "email",property = "email"),
            @Result(column = "status",property = "status"),
            @Result(column = "apartment_id", property = "apartment", javaType = Apartment.class,
                    one = @One(select = "cn.com.betacat.dao.ApartmentMapper.selectById"))
    })
    Student selectStudentAndApartmentById(String id);


    @Select("select * from student")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "apartment_id", property = "apartmentId"),
            @Result(column = "class_name", property = "className"),
            @Result(column = "name", property = "name"),
            @Result(column = "age", property = "age"),
            @Result(column = "sex",property = "sex"),
            @Result(column = "phone",property = "phone"),
            @Result(column = "email",property = "email"),
            @Result(column = "status",property = "status"),
            @Result(column = "apartment_id", property = "apartment", javaType = Apartment.class,
                    one = @One(select = "cn.com.betacat.dao.ApartmentMapper.selectById"))
    })
    List<Student> selectAllStudentsAndApartment();

}
