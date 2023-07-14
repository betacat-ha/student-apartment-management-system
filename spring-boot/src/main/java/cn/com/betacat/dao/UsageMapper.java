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
import cn.com.betacat.pojo.Usage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UsageMapper extends BaseMapper<Usage> {
    @Select("select * from wae_usage")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "apartmentId", column = "apartment_id"),
            @Result(property = "type", column = "type"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "status", column = "status"),
            @Result(property = "apartment", column = "apartment_id", javaType = Apartment.class,
                    one = @One(select = "cn.com.betacat.dao.ApartmentMapper.selectById")),
            @Result(property = "bill", column = "id", javaType = cn.com.betacat.pojo.Bill.class,
                    one = @One(select = "cn.com.betacat.dao.BillMapper.selectByUsageId"))
    })
    List<Usage> selectAllUsages();

    @Select("select * from wae_usage")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "apartmentId", column = "apartment_id"),
            @Result(property = "type", column = "type"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "status", column = "status"),
            @Result(property = "apartment", column = "apartment_id", javaType = Apartment.class,
                    one = @One(select = "cn.com.betacat.dao.ApartmentMapper.selectApartmentAndStudentsById")),
            @Result(property = "bill", column = "id", javaType = cn.com.betacat.pojo.Bill.class,
                    one = @One(select = "cn.com.betacat.dao.BillMapper.selectByUsageId"))
    })
    List<Usage> selectAllUsagesAndApartmentAndStudentsAndBill();

    @Select("SELECT * FROM wae_usage WHERE apartment_id IN (SELECT id FROM apartment WHERE building_id = #{building_id})")
    List<Usage> getUsageByBuildingId(@Param("building_id") Integer buildingId);
}
