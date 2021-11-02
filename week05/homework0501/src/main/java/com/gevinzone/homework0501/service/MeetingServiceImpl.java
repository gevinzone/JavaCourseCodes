package com.gevinzone.homework0501.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service(value = "meetingService")
public class MeetingServiceImpl implements IMeetingService {
    @Override
    public void startMeeting() {
        log.info("确认会议时间");
        log.info("确认与会人员");
        log.info("会议开始...");
    }

    @Override
    public void finishMeeting() {
        log.info("会议总结");
        log.info("会议结束...");
    }
}
