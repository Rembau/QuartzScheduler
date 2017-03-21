package org.rembau.quartz;

import org.rembau.quartz.command.CommandClient;
import org.rembau.quartz.command.CommandServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 程序入口
 * @author Rembau.Lin
 *
 */
public class MainClass {
	private static final Logger logger = LoggerFactory.getLogger(MainClass.class);
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext(
					new String[]{"classpath:applicationContext.xml","classpath:applicationContext-quartz.xml"});
		Context.init(); 	//全局上下文 初始化。
        logger.info("start");
		if(args.length==0){
			new CommandClient().start();
		} else if(args[0].trim().equals("start")){
			logger.info("程序启动");
			new CommandServer().start();
		} else {
			new CommandClient(args[0]).start();
		}
	}
	//((SchedulerServiceImpl)schedulerService).operate();
	//执行业务逻辑...
	
	//设置高度任务
	//每10秒中执行调试一次
	
	//schedulerService.schedule("TEST_ONE","0/10 * * ? * * *"); 	
	//Date startTime = parse("2013-01-22 9:52:00");
	//Date endTime =  parse("2013-01-22 11:00:00");
    
	//2009-06-01 21:50:00开始执行调度
	//schedulerService.schedule(startTime);

	//2009-06-01 21:50:00开始执行调度，2009-06-01 21:55:00结束执行调试
	//schedulerService.schedule(startTime,endTime);
	
	//2009-06-01 21:50:00开始执行调度，执行5次结束
	//schedulerService.schedule(startTime,null,5);

	//2009-06-01 21:50:00开始执行调度，每隔20秒执行一次，执行5次结束
	//schedulerService.schedule(startTime,null,5,20);
	
	//等等，查看com.sundoctor.quartz.service.SchedulerService	
}
