package compile;

import org.quartz.JobExecutionContext;
import org.rembau.quartz.service.job.AdapterJobBean;

public class Test extends AdapterJobBean{
	public void test(){
		System.out.println("I am good!");
	}
	public static void main(String[] args) {
		System.out.println("compile access!");
	}
	@Override
	protected void executeInternal(JobExecutionContext arg0) {
		
	}
}
