package org.rembau.compile;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

/**
 * Created by rembau on 2017/3/21.
 */
//
//          易失性 volatility
//        一个易失性的 Job 是在程序关闭之后不会被持久化。一个 Job 是通过调用 JobDetail 的 setVolatility(true)被设置为易失.
//        Job易失性的默认值是 false.
//        注意:只有采用持久性JobStore时才有效
//
//        Job 持久性 durability
//        设置JobDetail 的 setDurability(false)，在所有的触发器触发之后JobDetail将从 JobStore 中移出。
//        Job持久性默认值是false.
//        Scheduler将移除没有trigger关联的jobDetail
//
//        Job 可恢复性 shuldRecover
//        当一个Job在执行中，Scheduler非正常的关闭，设置JobDetail 的setRequestsRecovery(true) 在 Scheduler 重启之后可恢复的Job还会再次被执行。这个
//        Job 会重新开始执行。注意job代码事务特性.
//        Job可恢复性默认为false，Scheduler不会试着去恢复job操作
//不允许并发执行
@DisallowConcurrentExecution
public class TestJob implements Job {
    private final static Logger logger = LoggerFactory.getLogger(TestJob.class);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public TestJob() {
        logger.info("初始化TestJob");
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("test ======================== start" + jobExecutionContext.getJobDetail().getKey() + " " + simpleDateFormat.format(jobExecutionContext.getScheduledFireTime()));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("test ======================== end" + jobExecutionContext.getJobDetail().getKey());
    }
}
