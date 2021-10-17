package com.gevinzone.demospringbootstarter;

import com.gevinzone.homework0501.configuration.DemoProperties;
import com.gevinzone.homework0501.service.IMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(DemoConfigurationProperties.class)
public class DemoAutoConfiguration {
    @Autowired
    DemoConfigurationProperties demoConfigurationProperties;

    @Autowired
    IMeetingService meetingService;

    @Autowired
    DemoProperties demoProperties;
}
