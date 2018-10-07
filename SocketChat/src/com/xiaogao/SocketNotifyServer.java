package com.xiaogao;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/*
 * 服务器，该类创建了serverSocket对象，绑定本地的8888端口，
 * 声明静态变量sessionMap来保存所有已连接的客户端，并给每个客户端分配一个编号
 */
public class SocketNotifyServer {
	//socket服务器
	ServerSocket serverSocket=null;
	//用来存放已连接的客户端的socket信息
	static Map<Integer,Socket> sessionMap=new HashMap<Integer,Socket>();
	
	public void socket(){
		try {
			//创建serverSocket，绑定端口8989
			serverSocket =new ServerSocket(8989);
			System.out.println("服务器开启!!!");
			//客户端编号
			int i=1;
			//实现多个客户端的连接
			while(true){
				Socket socket=serverSocket.accept();
				System.out.println("客户端："+i+"连接成功~~~");
				if(socket!=null){
					//将socket放入map，key为客户端编号
					sessionMap.put(i, socket);
					//开启线程处理本次对话
					Thread thread=new Thread(new NotifyHandler(socket,sessionMap));
					thread.setDaemon(true);
					thread.start();//启动并运行线程
					i++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws IOException{
		new SocketNotifyServer().socket();
		
	}

}
