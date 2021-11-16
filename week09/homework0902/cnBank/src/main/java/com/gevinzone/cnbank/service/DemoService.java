package com.gevinzone.cnbank.service;

import com.gevinzone.remittancecontract.IDemo;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(version = "1.0.0", tag = "cnBank", weight = 100)
public class DemoService implements IDemo {
    @Override
    public String greeting() {
        return "greeting from cnBank!";
    }
}
