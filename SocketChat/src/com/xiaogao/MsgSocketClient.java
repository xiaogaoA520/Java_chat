package com.xiaogao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/*
 * 客户端
 */
public class MsgSocketClient {
	Socket socket=null;
	InputStreamReader input=null;
	InputStream in=null;
	OutputStream out=null;
	
	public void socketStart(){
		try {
			socket=new Socket("127.0.0.1",8989);
			System.out.println("客户端1启动————");
			//接受返回的数据
			new Thread(){
				public void run(){
					try {
						while(true){
							in=socket.getInputStream();
							ObjectInputStream ois=new ObjectInputStream(in);
							Message msg=(Message)ois.readObject();
							System.out.println("返回数据："+msg.getMsg());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
			out=socket.getOutputStream();
			ObjectOutputStream oos=new ObjectOutputStream(out);
			while(true){
				input=new InputStreamReader(System.in);//阻塞着等待输入
				String msg=new BufferedReader(input).readLine();
				Message message=new Message();
				message.setId(2);
				message.setMsg(msg);
				oos.writeObject(message);
				System.out.println("已发送");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//关闭流和连接
			try {
				in.close();
				out.close();
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new MsgSocketClient().socketStart();
	}

}

/*
 * 当(客户端1)启动的时候id属性的值为2，当(客户端2)启动时，id属性为1。
 * 此处把消息接收代码放在前面，是因为while (true){input = new InputStreamReader(System.in);
 * 这里在阻塞着等待输入，如果接收消息的代码放在这段代码下方，会导致在没有发过消息的时候，
 * 接收消息的线程不会启动，收不到别的客户端发来的消息
*/