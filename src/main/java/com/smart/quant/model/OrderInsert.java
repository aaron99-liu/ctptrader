package com.smart.quant.model;

import java.math.BigDecimal;

import com.smart.quant.annotation.Validation;

public class OrderInsert {
	String brokerID;	///期货公司代码
	String investorID;	///投资者代码	
	String instrumentID;	///合约代码	
	@Validation(nullable=false)
	String orderRef;	///报单引用   子账户系统订单号
	String userID;	///用户代码
	@Validation(nullable=false,checkValues= {"1", "2"})
	String orderPriceType;	///报单价格条件  1:市价，2:限价
	@Validation(nullable=false,checkValues= {"0", "1"})
	String direction;	///买卖方向	0:买， 1:卖
	@Validation(nullable=false,checkValues= {"0", "1", "2", "3", "4"})
	String combOffsetFlag;	///组合开平标志	0:开仓，1:平仓, 2:强平, 3:平今, 4:平昨
	@Validation(nullable=false,checkValues= {"1"})
	String combHedgeFlag = "1";	///组合投机套保标志	1:投机
	BigDecimal limitPrice;	///价格	
	@Validation(nullable=false)
	Integer volumeTotalOriginal;	///数量	
	@Validation(nullable=false,checkValues= {"0", "1", "2", "3", "4", "5"})
	String timeCondition;	///有效期类型	  1:立即完成，否则撤销，2:本节有效，3:当日有效，4:指定日期前有效，5:撤销前有效（永久生效）
	String GTDDate;	///GTD日期	
	@Validation(nullable=false,checkValues= {"1", "2", "3"})
	String volumeCondition;	///成交量类型	  1:任何数量，2:最小数量，3:全部数量
	Integer minVolume;	///最小成交量	
	@Validation(nullable=false,checkValues= {"1", "2", "3"})
	String contingentCondition;	///触发条件	1:立即，2:止损，3:止盈
	BigDecimal stopPrice;	///止损价
	@Validation(nullable=false,checkValues= {"0"})
	String forceCloseReason = "0";	///强平原因  0:非强平	
	@Validation(nullable=false)
	Integer isAutoSuspend;	///自动挂起标志	1:true, 0: false
	String businessUnit;	///业务单元
	Integer requestID;	///请求编号
	Integer userForceClose;	///用户强评标志	1:true, 0: false
	Integer isSwapOrder;	///互换单标志		1:true, 0: false
	String exchangeID;	///交易所代码	
	String investUnitID;	///投资单元代码	
	String accountID;	///资金账号	
	String currencyID;	///币种代码	
	String clientID;	///交易编码
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
	public String getInstrumentID() {
		return instrumentID;
	}
	public void setInstrumentID(String instrumentID) {
		this.instrumentID = instrumentID;
	}
	public String getOrderRef() {
		return orderRef;
	}
	public void setOrderRef(String orderRef) {
		this.orderRef = orderRef;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getOrderPriceType() {
		return orderPriceType;
	}
	public void setOrderPriceType(String orderPriceType) {
		this.orderPriceType = orderPriceType;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getCombOffsetFlag() {
		return combOffsetFlag;
	}
	public void setCombOffsetFlag(String combOffsetFlag) {
		this.combOffsetFlag = combOffsetFlag;
	}
	public String getCombHedgeFlag() {
		return combHedgeFlag;
	}
	public void setCombHedgeFlag(String combHedgeFlag) {
		this.combHedgeFlag = combHedgeFlag;
	}
	public BigDecimal getLimitPrice() {
		return limitPrice;
	}
	public void setLimitPrice(BigDecimal limitPrice) {
		this.limitPrice = limitPrice;
	}
	public Integer getVolumeTotalOriginal() {
		return volumeTotalOriginal;
	}
	public void setVolumeTotalOriginal(Integer volumeTotalOriginal) {
		this.volumeTotalOriginal = volumeTotalOriginal;
	}
	public String getTimeCondition() {
		return timeCondition;
	}
	public void setTimeCondition(String timeCondition) {
		this.timeCondition = timeCondition;
	}
	public String getGTDDate() {
		return GTDDate;
	}
	public void setGTDDate(String gTDDate) {
		GTDDate = gTDDate;
	}
	public String getVolumeCondition() {
		return volumeCondition;
	}
	public void setVolumeCondition(String volumeCondition) {
		this.volumeCondition = volumeCondition;
	}
	public Integer getMinVolume() {
		return minVolume;
	}
	public void setMinVolume(Integer minVolume) {
		this.minVolume = minVolume;
	}
	public String getContingentCondition() {
		return contingentCondition;
	}
	public void setContingentCondition(String contingentCondition) {
		this.contingentCondition = contingentCondition;
	}
	public BigDecimal getStopPrice() {
		return stopPrice;
	}
	public void setStopPrice(BigDecimal stopPrice) {
		this.stopPrice = stopPrice;
	}
	public String getForceCloseReason() {
		return forceCloseReason;
	}
	public void setForceCloseReason(String forceCloseReason) {
		this.forceCloseReason = forceCloseReason;
	}
	public Integer getIsAutoSuspend() {
		return isAutoSuspend;
	}
	public void setIsAutoSuspend(Integer isAutoSuspend) {
		this.isAutoSuspend = isAutoSuspend;
	}
	public Integer getIsSwapOrder() {
		return isSwapOrder;
	}
	public void setIsSwapOrder(Integer isSwapOrder) {
		this.isSwapOrder = isSwapOrder;
	}
	public Integer getUserForceClose() {
		return userForceClose;
	}
	public String getBusinessUnit() {
		return businessUnit;
	}
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}
	public Integer getRequestID() {
		return requestID;
	}
	public void setRequestID(Integer requestID) {
		this.requestID = requestID;
	}
	public Integer isUserForceClose() {
		return userForceClose;
	}
	public void setUserForceClose(Integer userForceClose) {
		this.userForceClose = userForceClose;
	}
	public String getExchangeID() {
		return exchangeID;
	}
	public void setExchangeID(String exchangeID) {
		this.exchangeID = exchangeID;
	}
	public String getInvestUnitID() {
		return investUnitID;
	}
	public void setInvestUnitID(String investUnitID) {
		this.investUnitID = investUnitID;
	}
	public String getAccountID() {
		return accountID;
	}
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	public String getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(String currencyID) {
		this.currencyID = currencyID;
	}
	public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
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
