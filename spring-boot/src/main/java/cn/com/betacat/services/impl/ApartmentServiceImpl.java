package cn.com.betacat.services.impl;

import cn.com.betacat.dao.ApartmentDao;
import cn.com.betacat.pojo.Apartment;
import cn.com.betacat.services.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartmentServiceImpl implements ApartmentService {
    @Autowired
    private ApartmentDao apartmentDao;

    /**
     * 查询公寓信息
     *
     * @return 公寓信息
     */
    @Override
    public List<Apartment> getApartment() {
        return apartmentDao.selectList(null);
    }

    /**
     * 查询所有公寓信息和对应的学生信息
     * @return 公寓信息
     */
    @Override
    public List<Apartment> getApartmentAndStudent() {
        return apartmentDao.selectAllApartmentsAndStudents();
    }
}
