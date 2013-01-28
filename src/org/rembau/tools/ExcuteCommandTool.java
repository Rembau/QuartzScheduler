package org.rembau.tools;

import java.util.HashMap;

import org.rembau.quartz.Context;
import org.rembau.quartz.ContextExcute;

public class ExcuteCommandTool {
	public static HashMap<String,String> analyse(String cmd,String command){
		command=command.replaceAll(cmd, "");
		//System.out.println(command);
		command=command.trim();
		HashMap<String,String> infoNames = new HashMap<String,String>();
		infoNames.put(ContextExcute.E_CRON,null);
		infoNames.put(ContextExcute.E_END_TIME,null);
		infoNames.put(ContextExcute.E_START_TIME,null);
		infoNames.put(ContextExcute.E_GROUP,null);
		infoNames.put(ContextExcute.E_JOBGROUP_NAME,null);
		infoNames.put(ContextExcute.E_JOB_CLASSNAME,null);
		infoNames.put(ContextExcute.E_NAME,null);
		infoNames.put(ContextExcute.E_JOB_NAME,null);
		infoNames.put(ContextExcute.E_START_TIME,null);
		String infos[]=command.split(" ");
		int i=0;
		boolean flag=false;
		String parameter="";
		while(i<infos.length){
			//System.out.println(infos[i]);
			if(infos[i].trim().equals("")){
				i++;
				continue;
			}
			if(infoNames.containsKey(infos[i])){
				if(!flag){	
					flag=true;
				} else{
					infoNames.put(parameter, "");
				}
				parameter=infos[i];
				i++;
				continue;
			}
			if(flag){
				infoNames.put(parameter, infos[i].trim());
				flag=false;
			} else {
				//System.out.println(infos[i]+" ");
			}
			i++;
		}
		return infoNames;
	}
	public static void main(String[] args) {
		System.out.println(analyse(Context.CMD_EXCUTE,"excute -c c -n -gn  -et et et -st st -jcn jcn -g g -jn jn"));
	}
}
