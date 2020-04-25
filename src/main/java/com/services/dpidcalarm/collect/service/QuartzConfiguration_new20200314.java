// package com.services.dpidcalarm.collect.service;
//
// /**
//  * @Description：
//  * @Author：zhangtao
//  * @Date：2019/11/2 0002 14:58:23
//  */
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
// import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
// import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//
// /**
//  * 配置任务
//  */
// @Configuration
// public class QuartzConfiguration_new20200314 {
//     private static Logger logger = LoggerFactory.getLogger(QuartzConfiguration_new20200314.class);
//
//
//
//     /**
//      * 调度工厂
//      * @return
//      */
//     @Bean(name = "scheduler")
//     public SchedulerFactoryBean schedulerFactory() {
//
//         //********************定义任务*********************//
//         MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
//
//         // 是否并发执行
//         jobDetail.setConcurrent(false);
//
//         // 设置任务的名字
//         jobDetail.setName("reptilianJob1");
//
//         // 设置任务的分组，在多任务的时候使用
//         jobDetail.setGroup("reptilianJobGroup");
//
//         // 需要执行的对象
//         QuartzTask quartzTask  = new QuartzTask();
//         jobDetail.setTargetObject(quartzTask);
//
//         //对两类类中的需要执行方法
//         jobDetail.setTargetMethod("reptilian1");
//
//
//         //***************触发器***************//
//         CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
//
//         //cron表达式，每整5分钟执行一次
//         //tigger.setCronExpression("0 0/5 * * * ? ");
//
//         //cron表达式，每整5秒执行一次
//         tigger.setCronExpression("*/5 * * * * ?");
//         logger.info("cron表达式，每整5钟执行一次");
//         tigger.setName("reptilianTrigger");
//
//
//         //***********调度器************//
//
//         SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
//
//         // 用于quartz集群,QuartzScheduler 启动时更新己存在的Job
//         factoryBean.setOverwriteExistingJobs(true);
//
//         // 延时启动，应用启动1秒后
//         factoryBean.setStartupDelay(1);
//
//         // 注册触发器
//         try {
//             factoryBean.getScheduler().scheduleJob(jobDetail.getObject(),tigger.getObject());
//         }catch (Exception e){
//             e.printStackTrace();
//         }
//
//
//         return factoryBean;
//     }
//
// }
