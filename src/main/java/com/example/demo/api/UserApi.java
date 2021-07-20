package com.example.demo.api;

import com.example.demo.business.UserBusiness;
import com.example.demo.entity.User;
import com.example.demo.exception.BaseException;
import com.example.demo.model.MLoginRequest;
import com.example.demo.model.MRegisterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserApi {

    // Method 1 : Filed Injection
    // @Autowired
//    public UserBusiness testBusiness;

    // Method 2 : Constructor Injection ประสิทธิภาพไวกว่า
    private final UserBusiness testBusiness;
    public UserApi(UserBusiness testBusiness) {
        this.testBusiness = testBusiness;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MLoginRequest mLoginRequest) throws BaseException {
        String response = testBusiness.login(mLoginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @RequestMapping("/register")
    public ResponseEntity<MRegisterResponse> mRegisterRequest(@RequestBody User request) throws BaseException {
        MRegisterResponse response = testBusiness.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<String> uploadProfilePicture(@RequestPart MultipartFile file) throws BaseException {
        String response = testBusiness.uploadProfilePicture(file);
        return ResponseEntity.ok(response);
    }

}
