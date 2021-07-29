package com.example.demo.api;

import com.example.demo.business.UserBusiness;
import com.example.demo.entity.User;
import com.example.demo.exception.BaseException;
import com.example.demo.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserApi {

    // Method 1 : Filed Injection
    // @Autowired
    // public UserBusiness testBusiness;

    // Method 2 : Constructor Injection ประสิทธิภาพไวกว่า
    private final UserBusiness userBusiness;

    public UserApi(UserBusiness testBusiness) {
        this.userBusiness = testBusiness;
    }

    @PostMapping("/login")
    public ResponseEntity<MLoginResponse> login(@RequestBody MLoginRequest mLoginRequest) throws BaseException {
        MLoginResponse response = userBusiness.login(mLoginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @RequestMapping("/register")
    public ResponseEntity<MRegisterResponse> mRegisterRequest(@RequestBody User request) throws BaseException {
        MRegisterResponse response = userBusiness.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/activate")
    public ResponseEntity<MActivateResponse> activate(@RequestBody MActivateRequest mActivateRequest) throws BaseException {
        MActivateResponse response = userBusiness.activate(mActivateRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend-activation-email")
    public ResponseEntity<Void> activate(@RequestBody MResendActivationEmailRequest mResendActivationEmailRequest) throws BaseException {
        userBusiness.resendActivationEmail(mResendActivationEmailRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<String> refreshToken() throws BaseException {
        String response = userBusiness.refreshToken();
        return ResponseEntity.ok(response);
    }

}
