package org.rembau.quartz;

import java.util.HashMap;
import java.util.Map;

public class Context {
	public static int CMD_S_PORT=2000;
	public static String CMD_STOP="stop";
	public static String CMD_EXIT="exit";
	public static Map<String,String> cmdToOPerate = new HashMap<String,String>(); //命令集
	public static int NUMOFCLIENT=0;//客户端连接数
	public static String CMS_EXCUTE="excute";
}
