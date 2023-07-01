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

package cn.com.betacat.controller;

import cn.com.betacat.dao.ApartmentMapper;
import cn.com.betacat.entity.Apartment;
import cn.com.betacat.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class ApartmentController {
    @Autowired
    private ApartmentMapper apartmentMapper;

    @GetMapping("/api/apartment/with-students")
    public Result queryWithStudents() {
        List<Apartment> list = apartmentMapper.selectAllApartmentsAndStudents();
        return new Result(200, "OK", list);
    }

    @GetMapping("/api/apartment")
    public Result query() {
        List<Apartment> list = apartmentMapper.selectList(null);
        return new Result(200, "OK", list);
    }
}
