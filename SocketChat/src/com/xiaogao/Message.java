package com.xiaogao;

import java.io.Serializable;

/*
 * 消息类，该类只有两个属性：
 * 一个是接收消息的客户端的编号，一个是消息内容
 */
public class Message implements Serializable{
	//客户端编号
	int id;
	//消息内容
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
