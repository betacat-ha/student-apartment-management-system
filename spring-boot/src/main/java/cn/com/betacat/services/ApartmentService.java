package cn.com.betacat.services;

import cn.com.betacat.pojo.Apartment;

import java.util.List;

public interface ApartmentService {
    /**
     * 查询公寓信息
     * @return 公寓信息
     */
    List<Apartment> getApartment();

    /**
     * 查询所有公寓信息和对应的学生信息
     * @return 公寓信息
     */
    List<Apartment> getApartmentAndStudent();
}
