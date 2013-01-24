package org.rembau.quartz.command;

import java.util.HashMap;

import org.rembau.quartz.Context;

public class Operate extends IOperate{
	private static Operate operate;
	public static Operate instance(){
		return operate==null?operate = new Operate():operate;
	}
	public void selectOpreate(String command){
		if(command.startsWith(Context.CMS_EXCUTE)){
			HashMap<String,String> paramsMap = new HashMap<String,String>();
		}
	}
	public static void main(String[] args) {
		System.out.println(Operate.instance());
		System.out.println(Operate.instance());
	}
}
