package com.xiaogao;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/*
 * �����������ഴ����serverSocket���󣬰󶨱��ص�8888�˿ڣ�
 * ������̬����sessionMap���������������ӵĿͻ��ˣ�����ÿ���ͻ��˷���һ�����
 */
public class SocketNotifyServer {
	//socket������
	ServerSocket serverSocket=null;
	//������������ӵĿͻ��˵�socket��Ϣ
	static Map<Integer,Socket> sessionMap=new HashMap<Integer,Socket>();
	
	public void socket(){
		try {
			//����serverSocket���󶨶˿�8989
			serverSocket =new ServerSocket(8989);
			System.out.println("����������!!!");
			//�ͻ��˱��
			int i=1;
			//ʵ�ֶ���ͻ��˵�����
			while(true){
				Socket socket=serverSocket.accept();
				System.out.println("�ͻ��ˣ�"+i+"���ӳɹ�~~~");
				if(socket!=null){
					//��socket����map��keyΪ�ͻ��˱��
					sessionMap.put(i, socket);
					//�����̴߳����ζԻ�
					Thread thread=new Thread(new NotifyHandler(socket,sessionMap));
					thread.setDaemon(true);
					thread.start();//�����������߳�
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
