package com.gevinzone.homework0501.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HomeworkServiceImpl implements IHomeworkService {
    @Override
    public void assignHomework() {
        log.info("开始布置作业");
        log.info("今日工作为：...");
    }

    @Override
    public void checkHomework() {
        log.info("检查第一项");
        log.info("检查第二项");
    }
}
