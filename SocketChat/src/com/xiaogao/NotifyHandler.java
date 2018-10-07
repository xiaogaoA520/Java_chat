package com.xiaogao;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

/*
 * ��Ϣ�����࣬��д���췽����
 * �õ���Ϣ���ͷ��Լ����е������ӿͻ���
 */
public class NotifyHandler extends Thread{
	Socket socket=null;
	InputStream in=null;
	Map<Integer,Socket> sessionMap=null;
	
	public NotifyHandler(Socket socket,Map<Integer,Socket> sessionMap){
		this.socket=socket;
		this.sessionMap=sessionMap;
	}
	
	public void run(){
		try {
            in = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(in);
            // ʵ��һ�����Ӷ��ͨ��
            while (true) {
                try {
                    Message msg = (Message) ois.readObject();
                    System.out.println("��Ϣ���ܶ��󣺿ͻ���" + msg.getId() + "��\t��Ϣ���ݣ�" + msg.getMsg());
                    // ��������
                    try {
                        Socket targetSocket = sessionMap.get(msg.getId());
                        OutputStream out = targetSocket.getOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(out);
                        oos.writeObject(msg);
                        System.out.println("�������ת��");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (socket.isClosed()) {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        socket.close();
                        break;
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
