package org.rembau.compile;

import org.quartz.JobExecutionContext;
import org.rembau.quartz.service.job.AdapterJobBean;
import org.springframework.stereotype.Service;

@Service
public class Test extends AdapterJobBean{
	public void test(){
		System.out.println("I am good!");
	}
	public static void main(String[] args) {
		System.out.println("org.rembau.compile access!");
	}
	@Override
	protected void executeInternal(JobExecutionContext arg0) {
        System.out.println("I am good11!");
	}
}
