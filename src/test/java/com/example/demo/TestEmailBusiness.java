package com.example.demo;

import com.example.demo.business.EmailBussiness;
import com.example.demo.exception.BaseException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestEmailBusiness {

    @Autowired
    private EmailBussiness emailBussiness;


    @Order(1)
    @Test
    void testSendActivateEmail() throws BaseException {
        emailBussiness.sendActivateUserEmail(
                TestData.email,
                TestData.user,
                TestData.token
        );
    }

    interface TestData {

        String email = "piratwn@gmail.com";

        String user = "Pirat Wannasiripipat";

        String token = "aslk;fmjoivjeqirfa";
    }
}
