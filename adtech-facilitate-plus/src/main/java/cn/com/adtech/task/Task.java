package cn.com.adtech.task;

import cn.com.adtech.service.HypertensionFollowUpService;
import cn.com.adtech.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@EnableAsync
public class Task {
    @Autowired
    private TaskService taskService;


    @Scheduled(cron = "0 0/5 * * * ? ")//5分钟都执行
    //定时明细机构和基层任务汇总
    @Async
    public void taskSource() {
        taskService.taskSource();
    }

    //定时明细居民数据汇总
    @Scheduled(cron = "0 0/5 * * * ? ")//5分钟都执行
    @Async
    public void taskresident() {
        taskService.taskresident();

    }


}