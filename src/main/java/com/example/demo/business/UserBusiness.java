package com.example.demo.business;

import com.example.demo.entity.User;
import com.example.demo.exception.BaseException;
import com.example.demo.exception.UserException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.*;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import com.example.demo.util.SecurityUtil;
import io.netty.util.internal.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
@Log4j2
public class UserBusiness {

    private final UserService userService;

    private final TokenService tokenService;

    private final UserMapper userMapper;

    private final EmailBussiness emailBussiness;


    public UserBusiness(UserService userService, TokenService tokenService, UserMapper userMapper, EmailBussiness emailBussiness) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.userMapper = userMapper;
        this.emailBussiness = emailBussiness;
    }

    public MLoginResponse login(MLoginRequest mLoginRequest) throws BaseException {
        Optional<User> opt = userService.findByEmail(mLoginRequest.getEmail());

        if (opt.isEmpty()) {
            throw UserException.loginFailEmailNotFound();
        }

        User user = opt.get();

        //Verify password
        if (!userService.matchPassword(mLoginRequest.getPassword(), user.getPassword())) {
            throw UserException.loginFailPasswordIncorrect();
        }

        //Verify activate status
        if(!user.isActivated()){
            throw UserException.loginFailUserUnactivated();
        }

        // TODO: generate JWT
        MLoginResponse response = new MLoginResponse();
        response.setToken(tokenService.tokenize(user));
        return response;
    }

    public String refreshToken() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }

        String userId = opt.get();

        Optional<User> optUser = userService.findById(userId);
        if (optUser.isEmpty()) {
            throw UserException.notFound();
        }

        User user = optUser.get();
        String token  = SecurityUtil.generateToken();
        Date tokenExpire = nextMinute(30);

        return tokenService.tokenize(user);
    }

    public MRegisterResponse register(User request) throws BaseException {
        String token  = SecurityUtil.generateToken();
        User user = userService.saveUser(request.getEmail(), request.getPassword(), request.getName(), token, nextMinute(30));

        sendEmail(user);

        // TODO: mapper
        return userMapper.toRegisterResponse(user);
    }

    public MActivateResponse activate(MActivateRequest mActivateRequest) throws BaseException {
        String token = mActivateRequest.getToken();
        if(StringUtil.isNullOrEmpty(token)){
            throw UserException.activateNoToken();
        }
        Optional<User> opt = userService.findByToken(token);
        if(opt.isEmpty()){
            throw UserException.activateFail();
        }

        User user = opt.get();
        if(user.isActivated()){
            throw UserException.activateAlready();
        }
        Date tokenExpire = user.getTokenExpire();
        Date date = new Date();
        if(date.after(tokenExpire)){
            throw UserException.activateTokenExpire();
        }

        user.setActivated(true);
        userService.update(user);

        MActivateResponse response = new MActivateResponse();
        response.setSuccess(true);
        return response;
    }

    public void resendActivationEmail(MResendActivationEmailRequest request) throws BaseException {
        String email = request.getEmail();
        if(StringUtil.isNullOrEmpty(email)){
            throw UserException.resendActivationEmailNoEmail();
        }

        Optional<User> opt = userService.findByEmail(email);
        if(opt.isEmpty()){
            throw UserException.resendActivationEmailNotFound();
        }

        User user = opt.get();

        if(user.isActivated()){
            throw UserException.activateAlready();
        }

        user.setToken(SecurityUtil.generateToken());
        user.setTokenExpire(nextMinute(30));
        user = userService.update(user);
        sendEmail(user);
    }

    //tokenExpire
    private Date nextMinute(int minute){
        Calendar calendar =Calendar.getInstance();
        calendar.add(Calendar.MINUTE,minute);
        return calendar.getTime();
    }

    private void sendEmail(User user) {
        String token = user.getToken();
        try {
            emailBussiness.sendActivateUserEmail(user.getEmail(), user.getName(), token);
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }
}
