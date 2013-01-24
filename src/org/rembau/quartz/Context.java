package org.rembau.quartz;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.rembau.quartz.tools.PropertiesTool;

public class Context {
	public static int CMD_S_PORT=2000;
	public static Map<String,String> cmdToOPerate = new HashMap<String,String>(); //命令集
	public static int NUMOFCLIENT=0;//客户端连接数
	public static String CMD_STOP="stop";
	public static String CMD_EXIT="exit";
	public static String CMD_EXCUTE="excute";
	/**
	 * 额外的编译文件路径
	 */
	public static String JOB_COMPILTEPATH="src"+File.separator+"compile";
	public static void init(){
		Properties params = PropertiesTool.getParams("conf"+File.separator+"extra.properties");
		JOB_COMPILTEPATH = params.getProperty("extraComilePath");
	}
}
