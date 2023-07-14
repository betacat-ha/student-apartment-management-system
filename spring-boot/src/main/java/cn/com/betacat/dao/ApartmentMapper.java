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

import cn.com.betacat.pojo.Apartment;
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
