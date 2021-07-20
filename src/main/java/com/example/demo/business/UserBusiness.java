package com.example.demo.business;

import com.example.demo.entity.User;
import com.example.demo.exception.BaseException;
import com.example.demo.exception.UserException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.MLoginRequest;
import com.example.demo.model.MRegisterResponse;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserBusiness {

    private final UserService userService;

    private final UserMapper userMapper;

    public UserBusiness(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public String login(MLoginRequest mLoginRequest) throws BaseException {
        Optional<User> opt = userService.findByEmail(mLoginRequest.getEmail());
        if (opt.isEmpty()) {
            throw UserException.loginFailEmailNotFound();
        }

        User user = opt.get();
        if (!userService.matchPassword(mLoginRequest.getPassword(), user.getPassword())) {
            throw UserException.loginFailPasswordIncorrect();
        }

        // TODO: generate JWT
        String token = "JWT";
        return token;
    }

    public MRegisterResponse register(User request) throws BaseException {
        User user = userService.saveUser(request.getEmail(), request.getPassword(), request.getName());
        // TODO: mapper
        return userMapper.toRegisterResponse(user);
    }
}
