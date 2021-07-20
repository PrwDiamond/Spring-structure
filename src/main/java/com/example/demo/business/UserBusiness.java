package com.example.demo.business;

import com.example.demo.entity.User;
import com.example.demo.exception.BaseException;
import com.example.demo.exception.FileException;
import com.example.demo.exception.UserException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.MLoginRequest;
import com.example.demo.model.MRegisterResponse;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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







    public String uploadProfilePicture(MultipartFile file) throws BaseException {
        if (file == null) {
            throw FileException.fileNull();
        }
        if (file.getSize() > 1048576 * 2) {
            throw FileException.fileMaxSize();
        }

        String contentType = file.getContentType();
        if (contentType == null) {
            throw FileException.unsupported();
        }

        List<String> supportedTypes = Arrays.asList("image/jpeg", "image/png");
//      เช็ค support ?
        if (!supportedTypes.contains(contentType)) {
            throw FileException.unsupported();
        }

//        TODO: upload file File Storage (AWS,S3,etc...)
        try {
            byte[] bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
