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
 * �ͻ���
 */
public class MsgSocketClient {
	Socket socket=null;
	InputStreamReader input=null;
	InputStream in=null;
	OutputStream out=null;
	
	public void socketStart(){
		try {
			socket=new Socket("127.0.0.1",8989);
			System.out.println("�ͻ���1������������");
			//���ܷ��ص�����
			new Thread(){
				public void run(){
					try {
						while(true){
							in=socket.getInputStream();
							ObjectInputStream ois=new ObjectInputStream(in);
							Message msg=(Message)ois.readObject();
							System.out.println("�������ݣ�"+msg.getMsg());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
			out=socket.getOutputStream();
			ObjectOutputStream oos=new ObjectOutputStream(out);
			while(true){
				input=new InputStreamReader(System.in);//�����ŵȴ�����
				String msg=new BufferedReader(input).readLine();
				Message message=new Message();
				message.setId(2);
				message.setMsg(msg);
				oos.writeObject(message);
				System.out.println("�ѷ���");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//�ر���������
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
 * ��(�ͻ���1)������ʱ��id���Ե�ֵΪ2����(�ͻ���2)����ʱ��id����Ϊ1��
 * �˴�����Ϣ���մ������ǰ�棬����Ϊwhile (true){input = new InputStreamReader(System.in);
 * �����������ŵȴ����룬���������Ϣ�Ĵ��������δ����·����ᵼ����û�з�����Ϣ��ʱ��
 * ������Ϣ���̲߳����������ղ�����Ŀͻ��˷�������Ϣ
*/