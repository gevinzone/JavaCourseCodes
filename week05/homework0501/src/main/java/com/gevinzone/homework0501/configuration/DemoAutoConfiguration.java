package com.gevinzone.homework0501.configuration;

import com.gevinzone.homework0501.aop.ObjectProxy;
import com.gevinzone.homework0501.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(DemoProperties.class)
@ConditionalOnClass({IMeetingService.class, IMeetingService.class, StudyService.class})
public class DemoAutoConfiguration {
    @Autowired
    DemoProperties demoProperties;

    @Bean(name = "proxiedMeetingService")
    public IMeetingService getMeetingService() {
        ObjectProxy proxy = new ObjectProxy();
        return  (IMeetingService) proxy.createProxy(new MeetingServiceImpl());
    }

}
