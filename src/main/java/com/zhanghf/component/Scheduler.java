package com.zhanghf.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Component
public class Scheduler {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     * 每隔3分钟执行一次
     */
    @Scheduled(fixedRate = 180000)
    public void brushFaceTasks() {
        log.info("每三分钟执行一次,date={}", dateFormat.format(new Date()));
    }

    /**
     * 每天凌晨1点执行一次
     */
    @Scheduled(cron = "0 00 01 * * ?")
    public void takeNumberTask() {
        log.info("凌晨一点执行,date={}", dateFormat.format(new Date()));
    }

}
