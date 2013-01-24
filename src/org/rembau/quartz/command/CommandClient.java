package org.rembau.quartz.command;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.rembau.quartz.Context;
import org.rembau.quartz.ContextExcute;
import org.rembau.quartz.service.job.AdapterJobBean;
import org.rembau.quartz.tools.CompileTool;
import org.rembau.quartz.tools.ExcuteCommandTool;

public class CommandClient extends Thread{
	private String cmd="";
	public CommandClient(){}
	public CommandClient(String str){
		this.cmd=str;
	}
	public void run(){
		if(cmd.length()==0){
			loop();
		} else if(cmd.equals(Context.CMD_STOP)){
			sendMessage();
		}
	}
	public void sendMessage(){
		Socket socket=null;
		DataOutputStream writeToSocket=null;
		try {
			socket = new Socket("127.0.0.1",Context.CMD_S_PORT);
			writeToSocket=new DataOutputStream(socket.getOutputStream());
			writeToSocket.writeBytes(cmd+"\n");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				writeToSocket.close();
				writeToSocket=null;
				socket.close();
				socket=null;
			} catch (IOException e) {
			}
		}
	}
	public void loop(){
		Socket socket = null;
		DataOutputStream writeToSocket=null;
		BufferedReader readFromSocket=null;
		BufferedReader readFromSystem=null;
		try {
			socket = new Socket("127.0.0.1",Context.CMD_S_PORT);
			writeToSocket = new DataOutputStream(socket.getOutputStream());
			readFromSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			new ShowFeedback(readFromSocket).start();
			readFromSystem = new BufferedReader(new InputStreamReader(System.in));
			String cmd=readFromSystem.readLine();
			while(!cmd.equals("exit")){
				cmd=cmd.trim();
				if(!cmd.equals("")){
					label:{
						if(cmd.startsWith(Context.CMD_EXCUTE)){
							HashMap<String,String> parameter = ExcuteCommandTool.analyse(Context.CMD_EXCUTE,cmd);
							String job_classname = parameter.get(ContextExcute.E_JOB_CLASSNAME);
							//System.out.println("job_classname "+job_classname);
							if(job_classname == null || job_classname.trim().length()==0){
								System.out.println("请填写类名！");
								break label;
							}
							if(job_classname.charAt(0)<'A' || job_classname.charAt(0)>'Z'){
								System.out.println("类名首字母必须为大写字母。");
								break label;
							}
							AdapterJobBean job = (AdapterJobBean) CompileTool.compile(job_classname);
							if(job==null){
								System.out.println(job_classname+" is not found!");
								break label;
							} else {
								//System.out.println(job.test());
							}
						}
						writeToSocket.writeBytes(cmd+"\n");
					}
				}
				cmd =readFromSystem.readLine();
			}
			System.exit(0);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				writeToSocket.close();
				readFromSystem.close();
				writeToSocket=null;
				readFromSystem=null;
				socket.close();
				socket=null;
			} catch (IOException e) {
			}
		}
	}
	class ShowFeedback extends Thread{
		BufferedReader readFromSocket;
		ShowFeedback(BufferedReader readFromSocket){
			this.readFromSocket=readFromSocket;
		}
		public void run(){
			String feedbackStr;
			try {
				while((feedbackStr=readFromSocket.readLine())!=null){
					System.out.println(feedbackStr);
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			} finally{
				try {
					readFromSocket.close();
				} catch (IOException e) {
				}
			}
		}
	}
	public static void main(String[] args) {
		new CommandClient().run();
	}
}
