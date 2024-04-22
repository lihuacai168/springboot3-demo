package com.huacai.springbootdemo.impl;

import com.huacai.springbootdemo.dto.resp.UserOutDTO;
import com.huacai.springbootdemo.dto.req.UserInDTO;
import com.huacai.springbootdemo.mapper.UserMapper;
import lombok.val;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserServiceImpl {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserOutDTO getUserByEmail(String email) {
       return userMapper.findByEmail(email);
    }

    public UserOutDTO createUser(UserInDTO detail) {
         // 首先，检查电子邮件是否已经存在
        if (userMapper.countByEmail(detail.getEmail()) > 0) {
            // 如果已存在，则抛出异常或返回信息提示不能创建用户
            throw new DuplicateKeyException("This email already exists and cannot be used to create a new account.");
        }
        // 插入用户到数据库
        val i = userMapper.insertUser(detail);


        // 根据需求返回相应信息
        // 比如可以通过username再次查询用户信息
        UserInDTO insertedUser = userMapper.findById(i);

        UserOutDTO userOutDTO = new UserOutDTO();
        // 设定需要返回的用户信息
        userOutDTO.setUsername(insertedUser.getUsername());
        userOutDTO.setEmail(insertedUser.getEmail());

        return userOutDTO;
    }
}