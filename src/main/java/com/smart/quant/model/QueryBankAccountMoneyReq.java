package com.smart.quant.model;

public class QueryBankAccountMoneyReq {
	String tradeCode;	///业务功能码
	String bankID;	///银行代码
	String bankBranchID;	///银行分支机构代码
	String brokerID;	///期商代码
	String brokerBranchID;	///期商分支机构代码
	String tradeDate;	///交易日期
	String tradeTime;	///交易时间
	String bankSerial;	///银行流水号
	String tradingDay;	///交易系统日期 
	Integer plateSerial = 0;	///银期平台消息流水号
	String lastFragment;	///最后分片标志
	Integer sessionID = 0;	///会话号
	String customerName;	///客户姓名
	String idCardType;	///证件类型
	String identifiedCardNo;	///证件号码
	String custType;	///客户类型
	String bankAccount;	///银行帐号
	String bankPassWord;	///银行密码
	String accountID;	///投资者帐号
	String password;	///期货密码
	Integer installID = 0;	///安装编号
	Integer futureSerial = 0;	///期货公司流水号
	String userID;	///用户标识
	String verifyCertNoFlag;	///验证客户证件号码标志
	String currencyID;	///币种代码
	String digest;	///摘要
	String bankAccType;	///银行帐号类型
	String deviceID;	///渠道标志
	String bankSecuAccType;	///期货单位帐号类型
	String brokerIDByBank;	///期货公司银行编码
	String bankSecuAcc;	///期货单位帐号
	String bankPwdFlag;	///银行密码标志
	String secuPwdFlag;	///期货资金密码核对标志
	String operNo;	///交易柜员
	Integer TID = 0;	///交易ID
	String longCustomerName;	///长客户姓名
	public String getTradeCode() {
		return tradeCode;
	}
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
	public String getBankID() {
		return bankID;
	}
	public void setBankID(String bankID) {
		this.bankID = bankID;
	}
	public String getBankBranchID() {
		return bankBranchID;
	}
	public void setBankBranchID(String bankBranchID) {
		this.bankBranchID = bankBranchID;
	}
	public String getBrokerID() {
		return brokerID;
	}
	public void setBrokerID(String brokerID) {
		this.brokerID = brokerID;
	}
	public String getBrokerBranchID() {
		return brokerBranchID;
	}
	public void setBrokerBranchID(String brokerBranchID) {
		this.brokerBranchID = brokerBranchID;
	}
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getBankSerial() {
		return bankSerial;
	}
	public void setBankSerial(String bankSerial) {
		this.bankSerial = bankSerial;
	}
	public String getTradingDay() {
		return tradingDay;
	}
	public void setTradingDay(String tradingDay) {
		this.tradingDay = tradingDay;
	}
	public Integer getPlateSerial() {
		return plateSerial != null ? this.plateSerial : 0;
	}
	public void setPlateSerial(Integer plateSerial) {
		this.plateSerial = plateSerial;
	}
	public String getLastFragment() {
		return lastFragment;
	}
	public void setLastFragment(String lastFragment) {
		this.lastFragment = lastFragment;
	}
	public Integer getSessionID() {
		return sessionID != null ? this.sessionID : 0;
	}
	public void setSessionID(Integer sessionID) {
		this.sessionID = sessionID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getIdCardType() {
		return idCardType;
	}
	public void setIdCardType(String idCardType) {
		this.idCardType = idCardType;
	}
	public String getIdentifiedCardNo() {
		return identifiedCardNo;
	}
	public void setIdentifiedCardNo(String identifiedCardNo) {
		this.identifiedCardNo = identifiedCardNo;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBankPassWord() {
		return bankPassWord;
	}
	public void setBankPassWord(String bankPassWord) {
		this.bankPassWord = bankPassWord;
	}
	public String getAccountID() {
		return accountID;
	}
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getInstallID() {
		return installID != null  ? installID : 0;
	}
	public void setInstallID(Integer installID) {
		this.installID = installID;
	}
	public Integer getFutureSerial() {
		return futureSerial != null ? futureSerial : 0;
	}
	public void setFutureSerial(Integer futureSerial) {
		this.futureSerial = futureSerial;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getVerifyCertNoFlag() {
		return verifyCertNoFlag;
	}
	public void setVerifyCertNoFlag(String verifyCertNoFlag) {
		this.verifyCertNoFlag = verifyCertNoFlag;
	}
	public String getCurrencyID() {
		return currencyID;
	}
	public void setCurrencyID(String currencyID) {
		this.currencyID = currencyID;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public String getBankAccType() {
		return bankAccType;
	}
	public void setBankAccType(String bankAccType) {
		this.bankAccType = bankAccType;
	}
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public String getBankSecuAccType() {
		return bankSecuAccType;
	}
	public void setBankSecuAccType(String bankSecuAccType) {
		this.bankSecuAccType = bankSecuAccType;
	}
	public String getBrokerIDByBank() {
		return brokerIDByBank;
	}
	public void setBrokerIDByBank(String brokerIDByBank) {
		this.brokerIDByBank = brokerIDByBank;
	}
	public String getBankSecuAcc() {
		return bankSecuAcc;
	}
	public void setBankSecuAcc(String bankSecuAcc) {
		this.bankSecuAcc = bankSecuAcc;
	}
	public String getBankPwdFlag() {
		return bankPwdFlag;
	}
	public void setBankPwdFlag(String bankPwdFlag) {
		this.bankPwdFlag = bankPwdFlag;
	}
	public String getSecuPwdFlag() {
		return secuPwdFlag;
	}
	public void setSecuPwdFlag(String secuPwdFlag) {
		this.secuPwdFlag = secuPwdFlag;
	}
	public String getOperNo() {
		return operNo;
	}
	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}
	public Integer getTID() {
		return TID != null ? this.TID : 0;
	}
	public void setTID(Integer tID) {
		TID = tID;
	}
	public String getLongCustomerName() {
		return longCustomerName;
	}
	public void setLongCustomerName(String longCustomerName) {
		this.longCustomerName = longCustomerName;
	}
}
