package com.gevinzone.homework0501.configuration;

import com.gevinzone.homework0501.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(DemoProperties.class)
//@ConditionalOnClass({IMeetingService.class, IMeetingService.class, StudyService.class})
public class DemoAutoConfiguration {
    @Autowired
    DemoProperties demoProperties;

    @Bean
    public IMeetingService getMeetingService() {
        return new MeetingServiceImpl();
    }

//    @Resource(name = "meetingService")
//    @Autowired
//    public IMeetingService meetingService;
//
//    @Autowired
//    IHomeworkService homeworkService;
//
//    @Autowired
//    StudyService studyService;
}
