package org.rembau.quartz.service;

import org.quartz.*;
import org.rembau.compile.TestJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.impl.matchers.GroupMatcher.jobGroupEquals;

@Service("schedulerService")
public class SchedulerServiceImpl implements SchedulerService {
    private final static Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);

	private Scheduler scheduler;

	@Autowired
	public void setScheduler(@Qualifier("quartzScheduler") Scheduler scheduler) {
		this.scheduler = scheduler;
        try {
            scheduler.start();
            logger.info("start");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

	public void operate(){
//		try {
//			String triggerNames[]=scheduler.getTriggerGroupNames();
//			for(String triggerName:triggerNames){
//				scheduler.unscheduleJob(triggerName, Scheduler.DEFAULT_GROUP);
//			}
//			scheduler.shutdown();
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
	}
	@Override
	public void schedule(String cronExpression) {
		schedule(null, cronExpression);
	}

	@Override
	public void schedule(String name, String cronExpression) {
		try {
			schedule(name, new CronExpression(cronExpression));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void schedule(CronExpression cronExpression) {
		schedule(null, cronExpression);
	}

	@Override
	public void schedule(String name, CronExpression cronExpression) {
		if (name == null || name.trim().equals("")) {
			name = UUID.randomUUID().toString();
		}

	}

    @PostConstruct
    public void schedule() {
        System.out.println(11);
        show();
//        create();
//        delete();

    }

    private void show() {
        List<String> jobGroupNames = null;
        try {
            jobGroupNames = scheduler.getJobGroupNames();

            for (String jobGroup : jobGroupNames) {
                for(JobKey jobKey : scheduler.getJobKeys(jobGroupEquals(jobGroup))) {
                    logger.info("Found job identified by: " + jobKey);
                    List<? extends Trigger> triggersOfJob = scheduler.getTriggersOfJob(jobKey);
                    for (Trigger trigger : triggersOfJob) {
                        logger.info("found trigger by:{}, name:{}", jobKey, trigger.getKey());
                    }
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void delete() {
        try {
            scheduler.deleteJob(JobKey.jobKey("job2", "group1"));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void addJob() {
        JobDetail job = newJob(TestJob.class)
                .withIdentity("job3", "group1")
                .build();

//        scheduler.addJob(job);
    }

    private void create() {
        // Define job instance
        JobDetail job = newJob(TestJob.class)
                .withIdentity("job3", "group1")
                .build();

// Define a Trigger that will fire "now", and not repeat
        Trigger trigger = newTrigger()
                .withIdentity("trigger3", "group1")
                .withSchedule(cronSchedule("0/5 * * * * ?"))
                .build();

// Schedule the job with the trigger
        try {
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
