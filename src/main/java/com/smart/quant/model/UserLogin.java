package com.smart.quant.model;

public class UserLogin extends CtpRequest {
	private String front = "";
	private String tradingDay = "";
	private String brokerID = "";
	private String userID = "";
	private String password = "";
	private String userProductInfo = "";
	private String interfaceProductInfo = "";
	private String protocolInfo = "";
	private String macAddress = "";
	private String oneTimePassword = "";
	private String clientIPAddress = "";
	private String loginRemark = "";
	public String getFront() {
		return front;
	}
	public void setFront(String front) {
		this.front = front;
	}
	public String getTradingDay() {
		return tradingDay;
	}
	public void setTradingDay(String tradingDay) {
		this.tradingDay = tradingDay;
	}
	public String getBrokerID() {
		return brokerID;
	}
	public void setBrokerID(String brokerID) {
		this.brokerID = brokerID;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserProductInfo() {
		return userProductInfo;
	}
	public void setUserProductInfo(String userProductInfo) {
		this.userProductInfo = userProductInfo;
	}
	public String getInterfaceProductInfo() {
		return interfaceProductInfo;
	}
	public void setInterfaceProductInfo(String interfaceProductInfo) {
		this.interfaceProductInfo = interfaceProductInfo;
	}
	public String getProtocolInfo() {
		return protocolInfo;
	}
	public void setProtocolInfo(String protocolInfo) {
		this.protocolInfo = protocolInfo;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getOneTimePassword() {
		return oneTimePassword;
	}
	public void setOneTimePassword(String oneTimePassword) {
		this.oneTimePassword = oneTimePassword;
	}
	public String getClientIPAddress() {
		return clientIPAddress;
	}
	public void setClientIPAddress(String clientIPAddress) {
		this.clientIPAddress = clientIPAddress;
	}
	public String getLoginRemark() {
		return loginRemark;
	}
	public void setLoginRemark(String loginRemark) {
		this.loginRemark = loginRemark;
	}
}
