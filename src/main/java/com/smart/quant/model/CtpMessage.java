package com.smart.quant.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class CtpMessage {
	private int requestID;
	private String account;
	private String name;
	private int retcode;
	private String message;
	private String broker;
	private JSON data;
	
	public CtpMessage(int requestID, String account, String name, String broker, int retcode, String message, JSON data) {
		super();
		this.requestID = requestID;
		this.account = account;
		this.name = name;
		this.broker = broker;
		this.retcode = retcode;
		this.message = message;
		if(data != null) {			
			this.data = data;
		}else {
			this.data = new JSONObject();
		}
	}
	
	public int getRequestID() {
		return requestID;
	}
	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRetcode() {
		return retcode;
	}
	public void setRetcode(int retcode) {
		this.retcode = retcode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public JSON getData() {
		return data;
	}
	public void setData(JSON data) {
		this.data = data;
	}

	public String getBroker() {
		return broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
	}
}
