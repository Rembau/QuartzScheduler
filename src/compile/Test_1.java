package compile;

import org.quartz.JobExecutionContext;
import org.rembau.quartz.service.job.AdapterJobBean;

public class Test_1 extends AdapterJobBean{

	public static void main(String[] args) {

	}

	@Override
	public void test() {
		System.out.println("I'm good too!");
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) {
		
	}

}
