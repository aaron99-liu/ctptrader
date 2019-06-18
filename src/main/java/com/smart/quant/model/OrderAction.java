package com.smart.quant.model;

import java.math.BigDecimal;

public class OrderAction {
	String brokerID;///经纪公司代码
	String investorID;///投资者代码
	Integer orderActionRef;///报单操作引用
	String orderRef;///报单引用
	Integer requestID;///请求编号
	Integer frontID;	///前置编号
	String sessionID;	///会话编号
	String exchangeID;	///交易所代码
	String orderSysID;	///报单编号
	String actionFlag;	///操作标志
	BigDecimal limitPrice;	///价格
	Integer volumeChange;	///数量变化
	String userID;	///用户代码
	String instrumentID;	///合约代码
	String investUnitID;	///投资单元代码
	String iPAddress;	///IP地址
	String macAddress;	///Mac地址
	public String getBrokerID() {
		return brokerID;
	}
	public void setBrokerID(String brokerID) {
		this.brokerID = brokerID;
	}
	public String getInvestorID() {
		return investorID;
	}
	public void setInvestorID(String investorID) {
		this.investorID = investorID;
	}
	public Integer getOrderActionRef() {
		return orderActionRef;
	}
	public void setOrderActionRef(Integer orderActionRef) {
		this.orderActionRef = orderActionRef;
	}
	public String getOrderRef() {
		return orderRef;
	}
	public void setOrderRef(String orderRef) {
		this.orderRef = orderRef;
	}
	public Integer getRequestID() {
		return requestID;
	}
	public void setRequestID(Integer requestID) {
		this.requestID = requestID;
	}
	public Integer getFrontID() {
		return frontID;
	}
	public void setFrontID(Integer frontID) {
		this.frontID = frontID;
	}
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public String getExchangeID() {
		return exchangeID;
	}
	public void setExchangeID(String exchangeID) {
		this.exchangeID = exchangeID;
	}
	public String getOrderSysID() {
		return orderSysID;
	}
	public void setOrderSysID(String orderSysID) {
		this.orderSysID = orderSysID;
	}
	public String getActionFlag() {
		return actionFlag;
	}
	public void setActionFlag(String sctionFlag) {
		this.actionFlag = sctionFlag;
	}
	public BigDecimal getLimitPrice() {
		return limitPrice;
	}
	public void setLimitPrice(BigDecimal limitPrice) {
		this.limitPrice = limitPrice;
	}
	public Integer getVolumeChange() {
		return volumeChange;
	}
	public void setVolumeChange(Integer volumeChange) {
		this.volumeChange = volumeChange;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getInstrumentID() {
		return instrumentID;
	}
	public void setInstrumentID(String instrumentID) {
		this.instrumentID = instrumentID;
	}
	public String getInvestUnitID() {
		return investUnitID;
	}
	public void setInvestUnitID(String investUnitID) {
		this.investUnitID = investUnitID;
	}
	public String getiPAddress() {
		return iPAddress;
	}
	public void setiPAddress(String iPAddress) {
		this.iPAddress = iPAddress;
	}
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
}
