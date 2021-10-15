package com.gevinzone.homework0501.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StudyService {
    public void learn() {
        log.info("学习语文...");
        log.info("学习数学...");
        log.info("学习英语...");
    }

    public void review() {
        log.info("复习语文...");
        log.info("复习数学...");
        log.info("复习英语...");
    }
}
