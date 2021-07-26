package com.example.demo.schedule;

import com.example.demo.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserSchedule {

    private final UserService userService;

    public UserSchedule(UserService userService) {
        this.userService = userService;
    }

    //TODO : Schedule note
    //1 --> second
    //2 --> minute
    //3 --> hour
    //4 --> day
    //5 --> month
    //6 --> year

    //Every Minute (UTC time)
    @Scheduled(cron = "0 * * * * *", zone = "Asia/Bangkok")
    public void testEveryMinute() {
//        log.info("Hello What's up?");
    }

    //Every 09.00 (Thai time)
    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Bangkok")
    public void testEveryNineAM() {
//        log.info("Hey Hoo!!!");
    }

}
