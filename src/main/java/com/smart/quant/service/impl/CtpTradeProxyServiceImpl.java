package com.smart.quant.service.impl;

import java.io.File;
import javax.annotation.Resource;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.smart.quant.model.BankTransferReq;
import com.smart.quant.model.OrderAction;
import com.smart.quant.model.OrderInsert;
import com.smart.quant.model.QueryBankAccountMoneyReq;
import com.smart.quant.model.Result;
import com.smart.quant.processor.CtpProcessor;
import com.smart.quant.service.CtpTradeProxyService;
import com.smart.quant.util.ParamUtils;

@Service
public class CtpTradeProxyServiceImpl implements CtpTradeProxyService {
	private static final Logger logger = LoggerFactory.getLogger(CtpTradeProxyServiceImpl.class);

	@Value("${ctp.log.path}")
	private String logPath;
	@Value("${ctp.quotation.log.path}")
	private String quotationLogPath;
	@Value("${ctp.login.timeout}")
	private long loginTimeout = 2000;
	@Resource
	private CtpProcessor ctpProcessor;

	private String getMessage(int retcode) {
		String message = null;
		switch (retcode) {
		case 0:
			message = "";
			break;
		case -1:
			message = "网络连接失败";
			break;
		case -2:
			message = "未处理请求超过许可数";
			break;
		case -3:
			message = "每秒发送请求数超过许可数";
			break;
		case -100:
			message = "登录已失效，请重新登录！";
			break;			
		default:
			message = "未知错误";
			break;
		}
		return message;
	}

	private void ensureLogPath(String userID) {
		String accountLogPath = logPath + (logPath.endsWith(File.separator) ? "" : File.separator)  + userID;
		File logFolder = new File(accountLogPath);
		if (logFolder.exists()) {
			if(!logFolder.isDirectory()) {
				logFolder.delete();
				logFolder.mkdirs();
			}
		}else {
			logFolder.mkdirs();
		}
	}


	@Override
	public Result userLogin(String frontServer, String brokerID, String userID, String password, String userProductInfo,
			String interfaceProductInfo, String protocolInfo, String macAddress, String oneTimePassword,
			String clientIPAddress, String loginRemark, int requestID) {
		Result result = new Result();
		ensureLogPath(userID);
		int retcode = ctpProcessor.tradeLogin(logPath, frontServer, brokerID, userID, password, userProductInfo, interfaceProductInfo, 
				protocolInfo, macAddress, oneTimePassword, clientIPAddress, loginRemark, requestID);
		result.setRetcode(retcode);
		result.setMessage("");
		if(retcode < 0) {
			logger.error("trade login error, retcode=" + retcode + ",msg=" + getMessage(retcode));
			result.setMessage(getMessage(retcode));
		}
		return result;
	}

	@Override
	public Result quotationUserLogin(String frontServer, String brokerID, String userID, String password, int requestID) {
		String accountLogPath = quotationLogPath + (quotationLogPath.endsWith(File.separator) ? "" : File.separator)  + userID;
		File logFolder = new File(accountLogPath);
		if (logFolder.exists()) {
			if(!logFolder.isDirectory()) {
				logFolder.delete();
				logFolder.mkdirs();
			}
		}else {
			logFolder.mkdirs();
		}
		int retcode = ctpProcessor.quotationLogin(logPath, frontServer, brokerID, userID, password, requestID);
		if(retcode < 0) {
			logger.error("quotation login error, retcode=" + retcode + ",msg=" + getMessage(retcode));
		}
		Result result = new Result();
		result.setRetcode(retcode);
		result.setMessage("行情登录成功！");
		if(retcode < 0) {
			logger.error("quotation login error, retcode=" + retcode + ",msg=" + getMessage(retcode));
			result.setMessage(getMessage(retcode));
		}
		return result;
	}

	@Override
	public Result userLogout(String brokerID, String userID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.tradeLogout(brokerID, userID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));			
		return result;
	}

	@Override
	public Result userPasswordUpdate(String brokerID, String userID, String oldPassword, String newPassword, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.userPasswordUpdate(brokerID, userID, oldPassword, newPassword, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result tradingAccountPasswordUpdate(String brokerID, String accountID, String oldPassword, String newPassword, String currencyID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.tradingAccountPasswordUpdate(brokerID, accountID, oldPassword,	newPassword, currencyID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result orderInsert(String brokerID, String investorID, OrderInsert order, int requestID) {
		Result result = ParamUtils.validateParam(order);
		if(result.getRetcode() < 0) {
			return result;
		}
		double limitPrice = order.getLimitPrice() != null ? order.getLimitPrice().doubleValue() : 0.0;
		double stopPrice = order.getStopPrice() != null ? order.getStopPrice().doubleValue() : 0.0;
		int userForceClose = order.getUserForceClose() != null ? order.getUserForceClose() : 0;
		int isSwapOrder = order.getIsSwapOrder() != null ? order.getIsSwapOrder() : 0;
		int minVolume = order.getMinVolume() != null ? order.getMinVolume() : 1;
		String orderRef = order.getOrderRef();
		logger.info("order insert begin, order=[" + ToStringBuilder.reflectionToString(order) + "], orderRef=" + orderRef);
		int retcode = ctpProcessor.orderInsert(order.getBrokerID(), order.getInvestorID(), order.getInstrumentID(), orderRef, order.getUserID(), order.getOrderPriceType(), order.getDirection(), 
				order.getCombOffsetFlag(), order.getCombHedgeFlag(), limitPrice, order.getVolumeTotalOriginal(), order.getTimeCondition(), order.getGTDDate(), order.getVolumeCondition(), minVolume,
				order.getContingentCondition(), stopPrice, order.getForceCloseReason(), order.getIsAutoSuspend(), order.getBusinessUnit(), order.getRequestID(), userForceClose, isSwapOrder, 
				order.getExchangeID(), order.getInvestUnitID(), order.getAccountID(), order.getCurrencyID(), order.getClientID(), order.getiPAddress(), order.getMacAddress());
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result orderAction(String brokerID, String investorID, OrderAction order, int requestID) {
		Result result = ParamUtils.validateParam(order);
		if(result.getRetcode() < 0) {
			return result;
		}
		double limitPrice = order.getLimitPrice() != null ? order.getLimitPrice().doubleValue() : 0.0;
		String orderRef = order.getOrderRef();
		int retcode = ctpProcessor.orderAction(order.getBrokerID(), order.getInvestorID(), order.getOrderActionRef(), orderRef, order.getRequestID(), order.getFrontID(), order.getSessionID(), order.getExchangeID(), 
				order.getOrderSysID(), order.getActionFlag(), limitPrice, order.getVolumeChange(), order.getUserID(), order.getInstrumentID(), order.getInvestUnitID(), order.getiPAddress(), order.getMacAddress());
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result queryMaxOrderVolume(String brokerID, String investorID, String instrumentID, String direction,String offsetFlag, String hedgeFlag, Integer maxVolume, String exchangeID, String investUnitID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.queryMaxOrderVolume(brokerID, investorID, instrumentID, StringUtils.isEmpty(direction) ? 'Z' : direction.charAt(0), StringUtils.isEmpty(offsetFlag) ? 'Z' : offsetFlag.charAt(0), hedgeFlag, maxVolume, exchangeID, investUnitID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qrySettlementInfo(String brokerID, String investorID, String tradingDay, String accountID,
			String currencyID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qrySettlementInfo(brokerID, investorID, tradingDay, accountID, currencyID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result SettlementInfoConfirm(String brokerID, String investorID, String confirmDate, String confirmTime, Integer settlementID, String accountID, String currencyID, int requestID) {
		Result result = new Result();
		int sid = -1;
		if(settlementID != null) {
			sid = settlementID;
		}
		int retcode = ctpProcessor.SettlementInfoConfirm(brokerID, investorID, confirmDate, confirmTime, sid, accountID,  currencyID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qrySettlementInfoConfirm(String brokerID, String investorID, String accountID, String currencyID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qrySettlementInfoConfirm(brokerID, investorID, accountID, currencyID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qryOrder(String BrokerID, String InvestorID, String InstrumentID, String ExchangeID, String OrderSysID, String InsertTimeStart, String InsertTimeEnd, String InvestUnitID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qryOrder(BrokerID, InvestorID, InstrumentID, ExchangeID, OrderSysID, InsertTimeStart, InsertTimeEnd, InvestUnitID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qryTrade(String brokerID, String investorID, String instrumentID, String exchangeID, String tradeID, String insertTimeStart, String insertTimeEnd, String investUnitID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qryTrade(brokerID, investorID, instrumentID, exchangeID, tradeID, insertTimeStart, insertTimeEnd, investUnitID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qryInvestorPosition(String brokerID, String investorID, String instrumentID, String exchangeID, String investUnitID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qryInvestorPosition(brokerID, investorID, instrumentID, exchangeID, investUnitID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qryInvestorPositionDetail(String brokerID, String investorID, String instrumentID, String exchangeID, String investUnitID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qryInvestorPositionDetail(brokerID, investorID, instrumentID, exchangeID, investUnitID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qryTradingAccount(String BrokerID, String InvestorID, String currencyID, String bizType, String accountID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qryTradingAccount(BrokerID, InvestorID, currencyID, bizType, accountID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qryInvestor(String BrokerID, String InvestorID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qryInvestor(BrokerID, InvestorID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qryTradingCode(String BrokerID, String InvestorID, String ExchangeID, String clientID, String clientIDType, String investUnitID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qryTradingCode(BrokerID, InvestorID, ExchangeID, clientID, clientIDType, investUnitID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qryInstrumentMarginRate(String brokerID, String investorID, String instrumentID, String hedgeFlag, String exchangeID, String investUnitID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qryInstrumentMarginRate(brokerID, investorID, instrumentID, hedgeFlag, exchangeID, investUnitID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qryInstrumentCommissionRate(String brokerID, String investorID, String instrumentID, String exchangeID, String investUnitID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qryInstrumentCommissionRate(brokerID, investorID, instrumentID, exchangeID, investUnitID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qryExchange(String brokerID, String investorID, String exchangeID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qryExchange(brokerID, investorID, exchangeID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qryProduct(String brokerID, String investorID, String productID, String productClass, String exchangeID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qryProduct(brokerID, investorID, productID, productClass, exchangeID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qryInstrument(String brokerID, String investorID, String instrumentID, String exchangeID, String exchangeInstID, String productID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qryInstrument(brokerID, investorID, instrumentID, exchangeID, exchangeInstID, productID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qryTransferBank(String brokerID, String investorID, String bankID, String bankBrchID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qryTransferBank(brokerID, investorID, bankID, bankBrchID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qryNotice(String brokerID, String investorID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qryNotice(brokerID, investorID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qryExchangeMarginRate(String brokerID, String investorID, String instrumentID, String hedgeFlag, String exchangeID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qryExchangeMarginRate(brokerID, investorID, instrumentID, hedgeFlag, exchangeID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qryExchangeMarginRateAdjust(String brokerID, String investorID, String instrumentID, String hedgeFlag, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qryExchangeMarginRateAdjust(brokerID, investorID, instrumentID, hedgeFlag, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qryInvestUnit(String brokerID, String investorID, String investUnitID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qryInvestUnit(brokerID, investorID, investUnitID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qryTransferSerial(String brokerID, String accountID, String bankID, String CurrencyID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qryTransferSerial(brokerID, accountID, bankID, CurrencyID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qryAccountregister(String brokerID, String accountID, String bankID, String bankBranchID, String CurrencyID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qryAccountregister(brokerID, accountID, bankID, bankBranchID, CurrencyID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qryContractBank(String brokerID, String investorID, String bankID, String bankBrchID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qryContractBank(brokerID, investorID, bankID, bankBrchID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qryTradingNotice(String brokerID, String investorID, String investUnitID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qryTradingNotice(brokerID, investorID, investUnitID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result fromBankToFutureByFuture(String brokerID, String investorID, BankTransferReq req, int requestID) {
		Result result = new Result();		
		double tradeAmount = req.getTradeAmount() != null ? req.getTradeAmount().doubleValue() : 0.0;
		double custFee = req.getCustFee() != null ? req.getCustFee().doubleValue() : 0.0;
		double brokerFee = req.getBrokerFee() != null ? req.getBrokerFee().doubleValue() : 0.0;
		double futureFetchAmount = req.getFutureFetchAmount() != null ? req.getFutureFetchAmount().doubleValue() : 0.0;		
		int retcode = ctpProcessor.fromBankToFutureByFuture(req.getTradeCode(), req.getBankID(), req.getBankBranchID(), req.getBrokerID(), req.getBrokerBranchID(), req.getTradeDate(), req.getTradeTime(), req.getBankSerial(), req.getTradingDay(), 
				req.getPlateSerial(), req.getLastFragment(), req.getSessionID(), req.getCustomerName(), req.getIdCardType(), req.getIdentifiedCardNo(), req.getCustType(), req.getBankAccount(), req.getBankPassWord(), req.getAccountID(), 
				req.getPassword(), req.getInstallID(), req.getFutureSerial(), req.getUserID(), req.getVerifyCertNoFlag(), req.getCurrencyID(), tradeAmount, futureFetchAmount, req.getFeePayFlag(), 
				custFee, brokerFee, req.getMessage(), req.getDigest(), req.getBankAccType(), req.getDeviceID(), req.getBankSecuAccType(), req.getBrokerIDByBank(), req.getBankSecuAcc(), req.getBankPwdFlag(), 
				req.getSecuPwdFlag(), req.getOperNo(), req.getTID(), req.getTransferStatus(), req.getLongCustomerName(), requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result fromFutureToBankByFuture(String brokerID, String investorID, BankTransferReq req, int requestID) {
		Result result = new Result();
		double tradeAmount = req.getTradeAmount() != null ? req.getTradeAmount().doubleValue() : 0.0;
		double custFee = req.getCustFee() != null ? req.getCustFee().doubleValue() : 0.0;
		double brokerFee = req.getBrokerFee() != null ? req.getBrokerFee().doubleValue() : 0.0;
		double futureFetchAmount = req.getFutureFetchAmount() != null ? req.getFutureFetchAmount().doubleValue() : 0.0;
		int retcode = ctpProcessor.fromFutureToBankByFuture(req.getTradeCode(), req.getBankID(), req.getBankBranchID(), req.getBrokerID(), req.getBrokerBranchID(), req.getTradeDate(), req.getTradeTime(), req.getBankSerial(), req.getTradingDay(), 
				req.getPlateSerial(), req.getLastFragment(), req.getSessionID(), req.getCustomerName(), req.getIdCardType(), req.getIdentifiedCardNo(), req.getCustType(), req.getBankAccount(), req.getBankPassWord(), req.getAccountID(), 
				req.getPassword(), req.getInstallID(), req.getFutureSerial(), req.getUserID(), req.getVerifyCertNoFlag(), req.getCurrencyID(), tradeAmount, futureFetchAmount, req.getFeePayFlag(), 
				custFee, brokerFee, req.getMessage(), req.getDigest(), req.getBankAccType(), req.getDeviceID(), req.getBankSecuAccType(), req.getBrokerIDByBank(), req.getBankSecuAcc(), req.getBankPwdFlag(), 
				req.getSecuPwdFlag(), req.getOperNo(), req.getTID(), req.getTransferStatus(), req.getLongCustomerName(), requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result queryBankAccountMoneyByFuture(String brokerID, String investorID, QueryBankAccountMoneyReq req, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.queryBankAccountMoneyByFuture(req.getTradeCode(), req.getBankID(), req.getBankBranchID(), req.getBrokerID(), req.getBrokerBranchID(), req.getTradeDate(), req.getTradeTime(), 
				req.getBankSerial(), req.getTradingDay(), req.getPlateSerial(), req.getLastFragment(), req.getSessionID(), req.getCustomerName(), req.getIdCardType(), req.getIdentifiedCardNo(), req.getCustType(), 
				req.getBankAccount(), req.getBankPassWord(), req.getAccountID(), req.getPassword(), req.getInstallID(), req.getFutureSerial(), req.getUserID(), req.getVerifyCertNoFlag(), req.getCurrencyID(), 
				req.getDigest(), req.getBankAccType(), req.getDeviceID(), req.getBankSecuAccType(), req.getBrokerIDByBank(), req.getBankSecuAcc(), req.getBankPwdFlag(), req.getSecuPwdFlag(), req.getOperNo(), 
				req.getTID(), req.getLongCustomerName(), requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public Result qryInstrumentOrderCommRate(String brokerID, String investorID, String instrumentID, int requestID) {
		Result result = new Result();
		int retcode = ctpProcessor.qryInstrumentOrderCommRate(brokerID, investorID, instrumentID, requestID);
		result.setRetcode(retcode);
		result.setMessage(getMessage(retcode));
		return result;
	}

	@Override
	public String getTradingDay(String brokerID, String userID) {
		return ctpProcessor.getTradingDay(brokerID, userID);
	}

}
