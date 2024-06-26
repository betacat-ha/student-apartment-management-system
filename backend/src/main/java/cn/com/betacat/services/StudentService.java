package cn.com.betacat.services;

import cn.com.betacat.pojo.Student;


public interface StudentService {
    /**
     * 使用手机号和密码获取学生信息
     * @param student 用户信息
     * @return student 用户信息
     */
    Student getStudentInfoByPhoneAndPwd(Student student);

    /**
     * 通过Token获取学生信息
     * @param token 用户token
     */
    Student getStudentInfoBy(String token);
}
