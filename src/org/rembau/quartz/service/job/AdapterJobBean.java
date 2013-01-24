package org.rembau.quartz.service.job;

import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public abstract class AdapterJobBean extends QuartzJobBean {
	public String e_start_time;
	public String e_end_time;
	public String e_cron;
	public String e_job_name;
	public String e_group_name;
	public String e_name;
	public String e_group;
	public abstract void test();
	protected abstract void executeInternal(JobExecutionContext arg0);
}
