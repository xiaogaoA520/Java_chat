package com.xiaogao;

import java.io.Serializable;

/*
 * ��Ϣ�࣬����ֻ���������ԣ�
 * һ���ǽ�����Ϣ�Ŀͻ��˵ı�ţ�һ������Ϣ����
 */
public class Message implements Serializable{
	//�ͻ��˱��
	int id;
	//��Ϣ����
	String msg;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
