package com.huacai.springbootdemo.mapper;

import com.huacai.springbootdemo.dto.req.UserInDTO;
import com.huacai.springbootdemo.dto.resp.UserOutDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    @Insert("INSERT INTO users(username, password, email) VALUES(#{username}, #{password}, #{email})")
    int insertUser(UserInDTO user);

    @Select("SELECT * FROM users WHERE email = #{email} order by id limit 1")
    UserOutDTO findByEmail(String email);

    @Select("SELECT COUNT(*) FROM users WHERE email = #{email}")
    int countByEmail(String email);

    @Select("SELECT * FROM users WHERE id = #{id}")
    UserInDTO findById(int id);
}