//package com.yw.ustwobacked.schedulding;
//
//import lombok.extern.slf4j.Slf4j;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.Trigger;
//import org.springframework.scheduling.TriggerContext;
//import org.springframework.scheduling.annotation.SchedulingConfigurer;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
//import org.springframework.scheduling.config.ScheduledTaskRegistrar;
//import org.springframework.scheduling.support.CronTrigger;
//
//import jakarta.annotation.Resource;
//
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.concurrent.ScheduledFuture;
//
//@Configuration
//@RefreshScope
//@Slf4j
//public class UsTwoTaskRegistrar implements SchedulingConfigurer {
//
//    @Value("${ustwo.robot.schedule.cron}")
//    private String cron;
//
//    @Resource
//    private UsTwoBotScheduler usTwoBotScheduler;
//
//    private volatile ScheduledFuture<?> scheduledFuture; // 保存当前任务的引用
//
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        // 动态注册任务
//        taskRegistrar.addTriggerTask(
//                () -> usTwoBotScheduler.sendTestMessage(),
//                triggerContext -> new CronTrigger(cron).nextExecution(triggerContext)
//        );
//    }
//
//    /**
//     * 更新 Cron 表达式并重新注册任务
//     */
//    public void updateCron(String newCron) {
//        this.cron = newCron; // 更新 Cron 表达式
//        if (scheduledFuture != null) {
//            scheduledFuture.cancel(true); // 取消当前任务
//        }
//        // 重新注册任务
//        ScheduledTaskRegistrar taskRegistrar = new ScheduledTaskRegistrar();
//        taskRegistrar.setTaskScheduler(taskScheduler()); // 设置任务调度器
//        taskRegistrar.addTriggerTask(
//                () -> usTwoBotScheduler.sendTestMessage(),
//                triggerContext -> new CronTrigger(cron).nextExecution(triggerContext)
//        );
//        taskRegistrar.afterPropertiesSet(); // 初始化任务调度器
//        log.info("执行天气预报 sendTestMessage()，当前时间: {}", LocalDateTime.now());
//        log.info("天气预报定时任务 cron 更新为：{}", newCron);
//    }
//
//    /**
//     * 提供一个任务调度器（可以是线程池）
//     */
//    private ThreadPoolTaskScheduler taskScheduler() {
//        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
//        scheduler.setPoolSize(5); // 设置线程池大小
//        scheduler.setThreadNamePrefix("DynamicTaskScheduler-");
//        scheduler.initialize();
//        return scheduler;
//    }
//}