package com.huacai.springbootdemo.controller;

import com.huacai.springbootdemo.core.Result;
import com.huacai.springbootdemo.dto.resp.UserOutDTO;
import com.huacai.springbootdemo.dto.req.UserInDTO;
import com.huacai.springbootdemo.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController()
public class UserController {

    @Resource
    private UserServiceImpl userServiceImpl;

    @GetMapping("/getUserByEmail")
    public Result<UserOutDTO> getUserByEmail(@RequestParam String email) {
        return Result.OK(userServiceImpl.getUserByEmail(email));
    }

    @PostMapping("/createUser")
    public ResponseEntity<Result<UserOutDTO>> createUser(@Valid @RequestBody UserInDTO userInDTO) {
        try {
            UserOutDTO response = userServiceImpl.createUser(userInDTO);
            return new ResponseEntity<>(Result.OK(response), HttpStatus.CREATED);
        } catch (DuplicateKeyException e) {
            return new ResponseEntity<>(Result.Error(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}