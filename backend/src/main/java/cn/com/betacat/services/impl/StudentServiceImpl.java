package cn.com.betacat.services.impl;

import cn.com.betacat.dao.StudentDao;
import cn.com.betacat.pojo.Student;
import cn.com.betacat.services.StudentService;
import cn.com.betacat.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentDao studentDao;

    /**
     * 使用手机号和密码获取学生信息
     *
     * @param student@return user 用户信息
     */
    @Override
    public Student getStudentInfoByPhoneAndPwd(Student student) {
        return studentDao.getByPhoneAndPassword(student);
    }

    /**
     * 通过Token获取学生信息
     *
     * @param token 用户token
     */
    @Override
    public Student getStudentInfoBy(String token) {
        String id = JwtUtils.parseJWT(token).get("id").toString();
        return studentDao.selectById(id);
    }
}
