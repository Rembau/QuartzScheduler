package org.rembau.quartz.command;

import java.util.HashMap;

import org.rembau.quartz.Context;
import org.rembau.quartz.ContextExcute;
import org.rembau.quartz.service.job.AdapterJobBean;
import org.rembau.quartz.tools.CompileTool;
import org.rembau.quartz.tools.ExcuteCommandTool;

public class Operate extends IOperate{
	private static Operate operate;
	public static Operate instance(){
		return operate==null?operate = new Operate():operate;
	}
	public void selectOpreate(String command){
		if(command.startsWith(Context.CMD_EXCUTE)){
			HashMap<String,String> params = new HashMap<String,String>();
			params = ExcuteCommandTool.analyse(Context.CMD_EXCUTE,command);
			String job_classname = params.get(ContextExcute.E_JOB_CLASSNAME);
			System.out.println("job_classname "+job_classname);
			AdapterJobBean job = (AdapterJobBean) CompileTool.compile(job_classname);
			job.test();
		}
	}
	public static void main(String[] args) {
		System.out.println(Operate.instance());
		System.out.println(Operate.instance());
	}
}
