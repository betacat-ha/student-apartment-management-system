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

package cn.com.betacat.dao;

import cn.com.betacat.constant.DaoConstant;
import cn.com.betacat.pojo.Apartment;
import cn.com.betacat.pojo.Student;
import cn.com.betacat.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentDao extends BaseMapper<Student> {
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
                    one = @One(select = DaoConstant.APARTMENT_SELECT_BY_ID))
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
                    one = @One(select = DaoConstant.APARTMENT_SELECT_BY_ID))
    })
    List<Student> selectAllStudentsAndApartment();

    @Select("select id, apartment_id, class_name, name, age, gender, phone, email, status, password " +
            "from student " +
            "where phone=#{phone} and password =#{password}")
    Student getByPhoneAndPassword(Student student);

}
