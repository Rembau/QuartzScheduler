package org.rembau.compile;

import org.quartz.JobExecutionContext;
import org.rembau.quartz.service.job.AdapterJobBean;

public class Test_2 extends AdapterJobBean{

	public static void main(String[] args) {

	}

	@Override
	public void test() {
		System.out.println("Who are you?");
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) {
		
	}

}
