//package com.yw.ustwobacked.schedulding;
//
//import jakarta.annotation.Resource;
//import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//
//@Component
//public class NacosConfigListener implements ApplicationListener<EnvironmentChangeEvent> {
//
//    @Resource
//    private UsTwoTaskRegistrar usTwoTaskRegistrar;
//
//    @Resource
//    private Environment environment;
//
//    @Override
//    public void onApplicationEvent(EnvironmentChangeEvent event) {
//        if (event.getKeys().contains("ustwo.robot.schedule.cron")) {
//            // 从环境中获取最新值
//            String newCron = environment.getProperty("ustwo.robot.schedule.cron");
//            usTwoTaskRegistrar.updateCron(newCron);
//        }
//    }
//}
