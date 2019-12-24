package com.services.dpidcalarm.collect.service;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/11/2 0002 14:58:23
 */
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 配置任务
 */
@Configuration
public class QuartzConfiguration {
    private static Logger logger = LoggerFactory.getLogger(QuartzConfiguration.class);
    /**
     *  配置任务
     * @param quartzTask QuartzTask为需要执行的任务
     * @return
     */
    @Bean(name = "reptilianJob")
    public MethodInvokingJobDetailFactoryBean detailFactoryBean(QuartzTask quartzTask) {

        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();

        // 是否并发执行
        jobDetail.setConcurrent(false);

        // 设置任务的名字
        jobDetail.setName("reptilianJob");

        // 设置任务的分组，在多任务的时候使用
        jobDetail.setGroup("reptilianJobGroup");

        // 需要执行的对象
        jobDetail.setTargetObject(quartzTask);

        /*
         * TODO  非常重要
         * 执行QuartzTask类中的需要执行方法
         */
        jobDetail.setTargetMethod("reptilian");
        return jobDetail;
    }

    /**
     * 定时触发器
     * @param reptilianJob 任务
     * @return
     */
    @Bean(name = "jobTrigger")
    public CronTriggerFactoryBean cronJobTrigger(JobDetail reptilianJob){
        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();

        tigger.setJobDetail(reptilianJob);

        //cron表达式，每整钟执行一次
        tigger.setCronExpression("0 0/5 * * * ? ");
        // tigger.setCronExpression("*/5 * * * * ?");
        logger.info("cron表达式，每整5钟执行一次");
        tigger.setName("reptilianTrigger");
        return tigger;
    }

    /**
     * 调度工厂
     * @param jobTrigger 触发器
     * @return
     */
    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory(Trigger jobTrigger) {

        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();

        // 用于quartz集群,QuartzScheduler 启动时更新己存在的Job
        factoryBean.setOverwriteExistingJobs(true);

        // 延时启动，应用启动1秒后
        factoryBean.setStartupDelay(1);

        // 注册触发器
        factoryBean.setTriggers(jobTrigger);
        return factoryBean;
    }

}
