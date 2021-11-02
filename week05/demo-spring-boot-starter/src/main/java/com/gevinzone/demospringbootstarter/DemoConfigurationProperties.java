package com.gevinzone.demospringbootstarter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "gevinzone.demo")
public class DemoConfigurationProperties {
    String message = "Hello, world";
}
