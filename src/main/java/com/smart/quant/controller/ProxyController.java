package com.smart.quant.controller;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.smart.quant.model.BankTransferReq;
import com.smart.quant.model.OrderAction;
import com.smart.quant.model.OrderInsert;
import com.smart.quant.model.QueryBankAccountMoneyReq;
import com.smart.quant.model.Result;
import com.smart.quant.model.UserLogin;
import com.smart.quant.service.CtpTradeProxyService;
import com.smart.quant.util.ParamUtils;
import com.smart.quant.util.RegexUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ProxyController {
	private static Logger logger = LoggerFactory.getLogger(ProxyController.class);
	
	@Resource
	private CtpTradeProxyService ctpTradeProxyService;

	private void resultToMap(Result result, Map<String, Object> map) {
		if(result == null) {
			logger.error("result to map error, result is null");
			return;
		}
		if(map == null) {
			logger.error("result to map error, map is null");
			return;
		}
		map.put("retcode", result.getRetcode());
		map.put("message", result.getMessage());
	}
	
	@PostMapping(path = "/userLogin", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public @ResponseBody Map<String, Object> postUserLogin(@ModelAttribute("userLogin") UserLogin loginRequest) {
		Map<String, Object> map = new HashMap<String, Object>();
		String tradeFront = loginRequest.getFront();
		if(StringUtils.isEmpty(tradeFront)) {
			map.put("retcode", -100);
			map.put("message", "front不能为空！");
		}
		if(!RegexUtils.checkCtpUri(tradeFront)) {
			map.put("retcode", -100);
			map.put("message", "front格式错误！");
		}
		logger.info("userLogin request execute, front=" + tradeFront + ", brokerID=" + loginRequest.getBrokerID() + ", userID=" + loginRequest.getUserID() + ", password=" + loginRequest.getPassword());
		Result result = ctpTradeProxyService.userLogin(tradeFront, loginRequest.getBrokerID(), loginRequest.getUserID(), loginRequest.getPassword(), loginRequest.getUserProductInfo(),
				loginRequest.getInterfaceProductInfo(), loginRequest.getProtocolInfo(), loginRequest.getMacAddress(), loginRequest.getOneTimePassword(), loginRequest.getClientIPAddress(),
				loginRequest.getLoginRemark(), loginRequest.getRequestID());
		resultToMap(result, map);
		return map;
	}
	
	@ModelAttribute("userLogin")
	public UserLogin getUserLogin() {
	    return new UserLogin();
	}
	
	@PostMapping(path = "/UserPasswordUpdate", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public @ResponseBody Map<String, Object> postUserPasswordUpdate(@RequestParam String BrokerID, @RequestParam String UserID, @RequestParam String OldPassword, 
    		@RequestParam String NewPassword, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("UserPasswordUpdate request execute, brokerID=" + BrokerID + ", userID=" + UserID + ", oldPassword=" + OldPassword + ",newPassword=" + NewPassword + ",requestID=" + RequestID);
		Result result = ctpTradeProxyService.userPasswordUpdate(BrokerID, UserID, OldPassword, NewPassword, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/TradingAccountPasswordUpdate", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public @ResponseBody Map<String, Object> postTradingAccountPasswordUpdate(@RequestParam String BrokerID, @RequestParam String AccountID, @RequestParam String OldPassword, 
    		@RequestParam String NewPassword, @RequestParam String CurrencyID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("TradingAccountPasswordUpdate request execute, brokerID=" + BrokerID + ", AccountID=" + AccountID + ", oldPassword=" + OldPassword + ",newPassword=" + NewPassword + ",CurrencyID=" + CurrencyID + ",requestID=" + RequestID);
		Result result = ctpTradeProxyService.tradingAccountPasswordUpdate(BrokerID, AccountID, OldPassword, NewPassword, CurrencyID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@ModelAttribute("orderInsert")
	public OrderInsert getOrderInsert() {
	    return new OrderInsert();
	}
	
	@PostMapping(path = "/OrderInsert", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public @ResponseBody Map<String, Object> postOrderInsert(@ModelAttribute("orderInsert") OrderInsert orderInsert) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("OrderInsert request param check begin...");
		Result result = ParamUtils.validateParam(orderInsert);
		if(result == null || result.getRetcode() == null || result.getRetcode() != 0) {
			resultToMap(result, map);
			return map;
		}
		logger.info("OrderInsert request execute, orderInsert=" + ToStringBuilder.reflectionToString(orderInsert));
		result = ctpTradeProxyService.orderInsert(orderInsert.getBrokerID(), orderInsert.getUserID(), orderInsert, orderInsert.getRequestID());
		resultToMap(result, map);
		return map;
	}
	
	@ModelAttribute("orderAction")
	public OrderAction getOrderAction() {
	    return new OrderAction();
	}
	
	@PostMapping(path = "/OrderAction", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public @ResponseBody Map<String, Object> postOrderAction(@ModelAttribute("orderAction") OrderAction orderAction) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("OrderAction request param check begin...");
		Result result = ParamUtils.validateParam(orderAction);
		if(result == null || result.getRetcode() == null || result.getRetcode() != 0) {
			resultToMap(result, map);
			return map;
		}
		logger.info("OrderAction request execute, orderAction=" + ToStringBuilder.reflectionToString(orderAction));
		result = ctpTradeProxyService.orderAction(orderAction.getBrokerID(), orderAction.getUserID(), orderAction, orderAction.getRequestID());
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QueryMaxOrderVolume", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQueryMaxOrderVolume(@RequestParam String BrokerID,@RequestParam String InvestorID,@RequestParam String InstrumentID,@RequestParam String Direction,@RequestParam String OffsetFlag,
			@RequestParam String HedgeFlag,@RequestParam(required=false) Integer MaxVolume,@RequestParam(required=false) String ExchangeID,
			@RequestParam(required=false) String InvestUnitID,@RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QueryMaxOrderVolume request execute, BrokerID=" + BrokerID + ",InvestorID=" + InvestorID + ",InstrumentID" + InstrumentID + ",Direction=" + Direction + ",OffsetFlag=" + OffsetFlag + ",HedgeFlag=" + HedgeFlag + ",MaxVolume=" + MaxVolume
				 + ",ExchangeID" + ExchangeID + ",InvestUnitID" + InvestUnitID + ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.queryMaxOrderVolume(BrokerID, InvestorID, InstrumentID, Direction, OffsetFlag, HedgeFlag, MaxVolume != null ? MaxVolume : 0, ExchangeID, InvestUnitID, RequestID);
//		this.subMessage("QueryMaxOrderVolume", BrokerID, InvestorID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QrySettlementInfo", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQrySettlementInfo(@RequestParam String BrokerID, @RequestParam String InvestorID, @RequestParam(required=false) String TradingDay, @RequestParam(required=false) String AccountID, @RequestParam(required=false) String CurrencyID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QrySettlementInfo request execute, BrokerID=" + BrokerID + ",InvestorID=" + InvestorID + ",TradingDay=" + TradingDay + ",AccountID=" + AccountID + ",CurrencyID=" + CurrencyID + ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.qrySettlementInfo(BrokerID, InvestorID, TradingDay, AccountID, CurrencyID, RequestID);
//		this.subMessage("QrySettlementInfo", BrokerID, InvestorID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	//SettlementInfoConfirm
	@PostMapping(path = "/SettlementInfoConfirm", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postSettlementInfoConfirm(@RequestParam String BrokerID, @RequestParam String InvestorID, @RequestParam(required=false) String ConfirmDate, @RequestParam(required=false) String ConfirmTime, @RequestParam(required=false) Integer SettlementID, 
			 @RequestParam(required=false) String AccountID,  @RequestParam(required=false) String CurrencyID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("SettlementInfoConfirm request execute, BrokerID=" + BrokerID + ",InvestorID=" + InvestorID + ",ConfirmDate=" + ConfirmDate + ",ConfirmTime=" + ConfirmTime + ",SettlementID=" + SettlementID + ",AccountID=" + AccountID + ",CurrencyID=" + CurrencyID + ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.SettlementInfoConfirm(BrokerID, InvestorID, ConfirmDate, ConfirmTime, SettlementID, AccountID, CurrencyID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QrySettlementInfoConfirm", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQrySettlementInfoConfirm(@RequestParam String BrokerID, @RequestParam String InvestorID, @RequestParam(required=false) String AccountID,  @RequestParam(required=false) String CurrencyID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QrySettlementInfoConfirm request execute, BrokerID=" + BrokerID + ",InvestorID=" + InvestorID + ",AccountID=" + AccountID + ",CurrencyID=" + CurrencyID + ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.qrySettlementInfoConfirm(BrokerID, InvestorID, AccountID, CurrencyID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QryOrder", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQryOrder(@RequestParam String BrokerID, @RequestParam String InvestorID, @RequestParam(required=false) String InstrumentID, @RequestParam(required=false) String ExchangeID, @RequestParam(required=false) String OrderSysID, @RequestParam(required=false) String InsertTimeStart, 
			 @RequestParam(required=false) String InsertTimeEnd,  @RequestParam(required=false) String InvestUnitID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QryOrder request execute, BrokerID=" + BrokerID + ",InvestorID=" + InvestorID + ",InstrumentID=" + InstrumentID + ",ExchangeID=" + ExchangeID + ",OrderSysID=" + OrderSysID + ",InsertTimeStart=" + InsertTimeStart + ",InsertTimeEnd=" + InsertTimeEnd
				 + ",InvestUnitID=" + InvestUnitID+ ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.qryOrder(BrokerID, InvestorID, InstrumentID, ExchangeID, OrderSysID, InsertTimeStart, InsertTimeEnd, InvestUnitID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QryTrade", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQryTrade(@RequestParam String BrokerID, @RequestParam String InvestorID, @RequestParam(required=false) String InstrumentID, @RequestParam(required=false) String ExchangeID, @RequestParam(required=false) String TradeID, @RequestParam(required=false) String TradeTimeStart, 
			 @RequestParam(required=false) String TradeTimeEnd,  @RequestParam(required=false) String InvestUnitID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QryTrade request execute, BrokerID=" + BrokerID + ",InvestorID=" + InvestorID + ",InstrumentID=" + InstrumentID + ",ExchangeID=" + ExchangeID + ",TradeID=" + TradeID + ",TradeTimeStart=" + TradeTimeStart + ",TradeTimeEnd=" + TradeTimeEnd
				 + ",InvestUnitID=" + InvestUnitID+ ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.qryTrade(BrokerID, InvestorID, InstrumentID, ExchangeID, TradeID, TradeTimeStart, TradeTimeEnd, InvestUnitID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QryInvestorPosition", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQryInvestorPosition(@RequestParam String BrokerID, @RequestParam String InvestorID, 
			@RequestParam(required=false) String InstrumentID, @RequestParam(required=false) String ExchangeID, @RequestParam(required=false) String InvestUnitID, 
			@RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QryInvestorPosition request execute, BrokerID=" + BrokerID + ",InvestorID=" + InvestorID + ",InstrumentID=" + InstrumentID + ",ExchangeID=" + ExchangeID + ",InvestUnitID=" + InvestUnitID+ ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.qryInvestorPosition(BrokerID, InvestorID, InstrumentID, ExchangeID, InvestUnitID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QryInvestorPositionDetail", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQryInvestorPositionDetail(@RequestParam String BrokerID, @RequestParam String InvestorID, @RequestParam(required=false) String InstrumentID, @RequestParam(required=false) String ExchangeID, @RequestParam(required=false) String InvestUnitID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QryInvestorPositionDetail request execute, BrokerID=" + BrokerID + ",InvestorID=" + InvestorID + ",InstrumentID=" + InstrumentID + ",ExchangeID=" + ExchangeID + ",InvestUnitID=" + InvestUnitID+ ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.qryInvestorPositionDetail(BrokerID, InvestorID, InstrumentID, ExchangeID, InvestUnitID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QryTradingAccount", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQryTradingAccount(@RequestParam String BrokerID, @RequestParam String InvestorID, @RequestParam(required=false) String CurrencyID, @RequestParam(required=false) String BizType, @RequestParam(required=false) String AccountID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QryTradingAccount request execute, BrokerID=" + BrokerID + ",InvestorID=" + InvestorID + ",CurrencyID=" + CurrencyID + ",BizType=" + BizType + ",AccountID=" + AccountID+ ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.qryTradingAccount(BrokerID, InvestorID, CurrencyID, BizType, AccountID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QryInvestor", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQryInvestor(@RequestParam String BrokerID, @RequestParam String InvestorID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QryInvestor request execute, BrokerID=" + BrokerID + ",InvestorID=" + InvestorID + ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.qryInvestor(BrokerID, InvestorID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QryInstrumentMarginRate", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQryInstrumentMarginRate(@RequestParam String BrokerID, @RequestParam String InvestorID, @RequestParam String InstrumentID, @RequestParam String HedgeFlag, @RequestParam(required=false) String ExchangeID, @RequestParam(required=false) String InvestUnitID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QryInstrumentMarginRate request execute, BrokerID=" + BrokerID + ",InvestorID=" + InvestorID + ",InstrumentID=" + InstrumentID + ",HedgeFlag=" + HedgeFlag + ",ExchangeID=" + ExchangeID + ",InvestUnitID=" + InvestUnitID + ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.qryInstrumentMarginRate(BrokerID, InvestorID, InstrumentID, HedgeFlag, ExchangeID, InvestUnitID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QryInstrumentCommissionRate", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQryInstrumentCommissionRate(@RequestParam String BrokerID, @RequestParam String InvestorID, @RequestParam String InstrumentID, @RequestParam(required=false) String ExchangeID, @RequestParam(required=false) String InvestUnitID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QryInstrumentCommissionRate request execute, BrokerID=" + BrokerID + ",InvestorID=" + InvestorID + ",InstrumentID=" + InstrumentID + ",ExchangeID=" + ExchangeID + ",InvestUnitID=" + InvestUnitID + ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.qryInstrumentCommissionRate(BrokerID, InvestorID, InstrumentID, ExchangeID, InvestUnitID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QryTradingCode", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQryTradingCode(@RequestParam String BrokerID, @RequestParam String InvestorID, @RequestParam(required=false) String ExchangeID, @RequestParam(required=false) String ClientID, @RequestParam(required=false) String ClientIDType, @RequestParam(required=false) String InvestUnitID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QryTradingCode request execute, BrokerID=" + BrokerID + ",InvestorID=" + InvestorID + ",ExchangeID=" + ExchangeID + ",ClientID=" + ClientID + ",ClientIDType=" + ClientIDType + ",InvestUnitID=" + InvestUnitID + ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.qryTradingCode(BrokerID, InvestorID, ExchangeID, ClientID, ClientIDType, InvestUnitID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QryExchange", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQryExchange(String BrokerID, String InvestorID, @RequestParam(required=false) String ExchangeID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QryExchange request execute, ExchangeID=" + ExchangeID + ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.qryExchange(BrokerID, InvestorID, ExchangeID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QryProduct", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQryProduct(String BrokerID, String InvestorID, @RequestParam(required=false) String ProductID, @RequestParam(required=false) String ProductClass, @RequestParam(required=false) String ExchangeID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QryProduct request execute, ProductID=" + ProductID + ",ProductClass=" + ProductClass+ ",ExchangeID=" + ExchangeID + ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.qryProduct(BrokerID, InvestorID, ProductID, ProductClass, ExchangeID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QryInstrument", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQryInstrument(String BrokerID, String InvestorID, @RequestParam(required=false) String InstrumentID, @RequestParam(required=false) String ExchangeID, @RequestParam(required=false) String ExchangeInstID, @RequestParam(required=false) String ProductID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QryInstrument request execute, InstrumentID=" + InstrumentID + ",ExchangeID=" + ExchangeID + ",ExchangeInstID=" + ExchangeInstID + ",ProductID=" + ProductID + ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.qryInstrument(BrokerID, InvestorID, InstrumentID, ExchangeID, ExchangeInstID, ProductID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QryTransferBank", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQryTransferBank(String BrokerID, String InvestorID, @RequestParam(required=false) String BankID, @RequestParam(required=false) String BankBrchID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QryTransferBank request execute, BankID=" + BankID + ",BankBrchID=" + BankBrchID + ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.qryTransferBank(BrokerID, InvestorID, BankID, BankBrchID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QryNotice", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQryNotice(String BrokerID, String InvestorID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QryNotice request execute, BrokerID=" + BrokerID + ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.qryNotice(BrokerID, InvestorID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QryExchangeMarginRate", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQryExchangeMarginRate(@RequestParam String BrokerID, String InvestorID, @RequestParam String InstrumentID, @RequestParam String HedgeFlag, @RequestParam String ExchangeID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QryExchangeMarginRate request execute, BrokerID=" + BrokerID + ",InstrumentID" + InstrumentID + ",HedgeFlag" + HedgeFlag + ",ExchangeID" + ExchangeID + ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.qryExchangeMarginRate(BrokerID, InvestorID, InstrumentID, HedgeFlag, ExchangeID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QryExchangeMarginRateAdjust", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQryExchangeMarginRateAdjust(@RequestParam String BrokerID, String InvestorID, @RequestParam String InstrumentID, @RequestParam String HedgeFlag, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QryExchangeMarginRateAdjust request execute, BrokerID=" + BrokerID + ",InstrumentID" + InstrumentID + ",HedgeFlag" + HedgeFlag + ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.qryExchangeMarginRateAdjust(BrokerID, InvestorID, InstrumentID, HedgeFlag, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QryInvestUnit", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQryInvestUnit(@RequestParam String BrokerID, @RequestParam String InvestorID, @RequestParam(required=false) String InvestUnitID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QryInvestUnit request execute, BrokerID=" + BrokerID + ",InvestorID" + InvestorID + ",InvestUnitID" + InvestUnitID + ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.qryInvestUnit(BrokerID, InvestorID, InvestUnitID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QryTransferSerial", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQryTransferSerial(@RequestParam String BrokerID, @RequestParam String AccountID, @RequestParam(required=false) String BankID, @RequestParam(required=false) String CurrencyID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QryTransferSerial request execute, BrokerID=" + BrokerID + ",AccountID" + AccountID + ",BankID" + BankID + ",CurrencyID" + CurrencyID + ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.qryTransferSerial(BrokerID, AccountID, BankID, CurrencyID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QryAccountregister", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQryAccountregister(@RequestParam String BrokerID, @RequestParam String AccountID, @RequestParam(required=false) String BankID, @RequestParam String BankBranchID, @RequestParam(required=false) String CurrencyID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QryAccountregister request execute, BrokerID=" + BrokerID + ",AccountID" + AccountID + ",BankID" + BankID + ",BankBranchID" + BankBranchID + ",CurrencyID" + CurrencyID + ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.qryAccountregister(BrokerID, AccountID, BankID, BankBranchID, CurrencyID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QryContractBank", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQryContractBank(@RequestParam String BrokerID, String InvestorID, @RequestParam(required=false) String BankID, @RequestParam(required=false) String BankBrchID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QryContractBank request execute, BrokerID=" + BrokerID + ",BankID" + BankID + ",BankBrchID" + BankBrchID + ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.qryContractBank(BrokerID, InvestorID, BankID, BankBrchID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QryTradingNotice", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQryTradingNotice(@RequestParam String BrokerID, @RequestParam(required=false) String InvestorID, @RequestParam(required=false) String InvestUnitID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QryTradingNotice request execute, BrokerID=" + BrokerID + ",InvestorID" + InvestorID + ",InvestUnitID" + InvestUnitID + ",RequestID" + RequestID);
		Result result = ctpTradeProxyService.qryTradingNotice(BrokerID, InvestorID, InvestUnitID, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QryInstrumentOrderCommRate", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
	public @ResponseBody Map<String, Object> postQryInstrumentOrderCommRate(@RequestParam String BrokerID, @RequestParam String InvestorID, @RequestParam(required=false) String InstrumentID, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QryInstrumentOrderCommRate request execute, BrokerID=" + BrokerID + ",InvestorID=" + InvestorID + ",InstrumentID=" + InstrumentID + ",RequestID=" + RequestID);
		Result result = ctpTradeProxyService.qryInstrumentOrderCommRate(BrokerID, InvestorID, InstrumentID, RequestID);
		resultToMap(result, map);
		return map;
	}
	

	@PostMapping(path = "/FromFutureToBankByFuture", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public @ResponseBody Map<String, Object> postFromFutureToBankByFuture(@ModelAttribute("bankTransferReq") BankTransferReq bankTransferReq, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("FromFutureToBankByFuture request execute, req=" + ToStringBuilder.reflectionToString(bankTransferReq));
		Result result = ctpTradeProxyService.fromFutureToBankByFuture(bankTransferReq.getBrokerID(), bankTransferReq.getUserID(), bankTransferReq, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@ModelAttribute("bankTransferReq")
	public BankTransferReq getBankTransferReq() {
	    return new BankTransferReq();
	}

	@PostMapping(path = "/FromBankToFutureByFuture", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public @ResponseBody Map<String, Object> postFromBankToFutureByFuture(@ModelAttribute("bankTransferReq") BankTransferReq bankTransferReq, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("FromBankToFutureByFuture request execute, req=" + ToStringBuilder.reflectionToString(bankTransferReq));
		Result result = ctpTradeProxyService.fromBankToFutureByFuture(bankTransferReq.getBrokerID(), bankTransferReq.getUserID(), bankTransferReq, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@PostMapping(path = "/QueryBankAccountMoneyByFuture", consumes= {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public @ResponseBody Map<String, Object> postQueryBankAccountMoneyByFuture(@ModelAttribute("queryBankAccountMoneyReq") QueryBankAccountMoneyReq bankTransferReq, @RequestParam Integer RequestID) {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.info("QueryBankAccountMoneyByFuture request execute, req=" + ToStringBuilder.reflectionToString(bankTransferReq));
		Result result = ctpTradeProxyService.queryBankAccountMoneyByFuture(bankTransferReq.getBrokerID(), bankTransferReq.getUserID(), bankTransferReq, RequestID);
		resultToMap(result, map);
		return map;
	}
	
	@ModelAttribute("queryBankAccountMoneyReq")
	public QueryBankAccountMoneyReq getQueryBankAccountMoneyReq() {
	    return new QueryBankAccountMoneyReq();
	}
}
