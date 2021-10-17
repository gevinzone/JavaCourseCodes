package com.gevinzone.startertest;

import com.gevinzone.demospringbootstarter.DemoConfigurationProperties;
import com.gevinzone.homework0501.service.IMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TestService {
    @Autowired
    private DemoConfigurationProperties demoConfigurationProperties;

    @Autowired
    private IMeetingService meetingService;

    public void test() {
        System.out.println(demoConfigurationProperties.getMessage());
    }

    public void test2() {
        meetingService.startMeeting();
        meetingService.finishMeeting();
    }
}
