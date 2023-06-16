package cn.com.betacat.dao;

import cn.com.betacat.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据电子邮箱查询用户
     */
    @Select("select * from user where email = #{email}")
    User selectByEmail(String email);

    @Select("select id, name, nickname, password, phone, email, role_id, create_time, last_login_time, status " +
            "from user " +
            "where email=#{email} and password =#{password}")
    User getByEmailAndPassword(User user);
}
