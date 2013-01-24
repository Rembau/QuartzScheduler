package org.rembau.quartz.command;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.rembau.quartz.Context;

public class CommandServer extends Thread{ 
	private Operate operate = Operate.instance();
	public void run(){
		ServerSocket ss=null;
		try {
			 ss =new ServerSocket(Context.CMD_S_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(true){
			Socket socket=null;
			try {
				socket = ss.accept();
				if(Context.NUMOFCLIENT==0){
					new ConnectionSocket(socket).start();
					Context.NUMOFCLIENT++;
				} else{
					new DataOutputStream(socket.getOutputStream()).
						writeBytes("A user is connected already!Remote Ip is "+socket.getRemoteSocketAddress()+".\n");
					socket.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	class ConnectionSocket extends Thread{
		private Socket socket;
		ConnectionSocket(Socket socket){
			this.socket=socket;
		}
		public void run(){
			BufferedReader readeFromSocket=null;
			DataOutputStream writeToSocket = null;
			try {
				readeFromSocket=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writeToSocket=new DataOutputStream(socket.getOutputStream());
				writeToSocket.writeBytes("Connect Accessed!\n");
				String command="";
				while((command=readeFromSocket.readLine())!=null){
					System.out.println(command);
					writeToSocket.writeBytes(command+" server's message!"+"\n");
					if(command.equals(Context.CMD_EXIT)){
						break;
					} else if(command.equals(Context.CMD_STOP)){
						//
					} else {
						operate.selectOpreate(command);
					}
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			} finally{
				Context.NUMOFCLIENT=0;
				try {
					readeFromSocket.close();
					readeFromSocket=null;
					socket.close();
					socket=null;
				} catch (IOException e) {
				}
			}
		}
	}
	public static void main(String[] args) {
		new CommandServer().start();
	}
}
