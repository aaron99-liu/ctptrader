package com.smart.quant.processor;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.smart.quant.util.SysUtil;

@Component
public class CtpProcessor {
	private static final Logger logger = LoggerFactory.getLogger(CtpProcessor.class);
	private static final List<String> contractList = new ArrayList<String>();
	private static boolean quotationStatus = false;
	private static final Object quotationSyncObject = new Object();
	public static void logger(String log) {
		logger.info(log);
	}
	public static void logger_error(String log) {
		logger.error(log);
	}

//	static {		
//		String libPath = System.getProperty("java.library.path");
//		logger.info(libPath);
//		System.loadLibrary("CtpProcessor");
//	}
	
	@Value("${config.lib.path}")
	private String libraryPath;
	
	@PostConstruct
	public void initJNI() {
//		String osName = SysUtil.getOsName();
//		if(!StringUtils.isEmpty(osName) && osName.contains("windows")) {			
//			System.load(libraryPath + "/CtpProcessor.dll");
//		}else if(!StringUtils.isEmpty(osName) && osName.contains("windows")) {
//			System.load(libraryPath + "/CtpProcessor.so");
//		}else {
//			logger.error("不支持的操作系统:" + osName);
//			System.exit(0);
//		}
		SysUtil.addLibraryDir(libraryPath);
		System.loadLibrary("CtpProcessor");
		if(!init()) {
			logger.error("ctpProcessor init error");
			return;
		}
	}
	
	public native boolean init();
	
	public native String getTradingDay(String brokerID, String userID);
	
	public native int subscribeQuotation(String[] contractCode);

	public native int quotationLogin(String logPath, String quotationServer, String brokerID, String userID, String password, int requestID);

	public native int tradeLogin(String logPath, String tradeServer, String brokerID, String userID, String password, String userProductInfo,
			String interfaceProductInfo, String protocolInfo, String macAddress, String oneTimePassword,
			String clientIPAddress, String loginRemark, int requestID);

	public native int tradeLogout(String brokerID, String userID, int requestID);

	public native int orderInsertCustom(String brokerID, String accountCode, String contractCode, String orderID, String userID, char orderPriceType, char bsFlag, char ocFlag, char hedgeFlag,
			double price, int volumeTotalOriginal, char timeCondition, char volumeCondition, int minVolume, char contingentCondition, double stopPrice, char forceCloseReason, int IsAutoSuspend, int requestID);
	
	public native int orderInsert(String brokerID, String investorID, String instrumentID, String orderRef, String userID, String orderPriceType, String direction, String combOffsetFlag, String combHedgeFlag, 
			double limitPrice, int volumeTotalOriginal, String timeCondition, String GTDDate, String volumeCondition, int minVolume, String contingentCondition, double stopPrice,
			String forceCloseReason, int isAutoSuspend, String businessUnit, int requestID, int userForceClose, int isSwapOrder, String exchangeID, String investUnitID, String accountID, 
			String currencyID, String clientID, String iPAddress, String macAddress);

	public native int ordreActionCustom(String brokerID, String userID, String accountCode, String orderSysID, String exchangeCode, String contractCode, char actionFlag, int requestID);
	
	public native int orderAction(String brokerID, String investorID, int oderActionRef, String orderRef, int requestID, int frontID, String sessionID, String exchangeID, String orderSysID, String actionFlag, double limitPrice, int volumeChange, String userID, 
			String instrumentID, String investUnitID, String IPAddress, String macAddress);
	
	public native int tradingAccountPasswordUpdate(String brokerID, String accountID, String oldPassword, String newPassword, String currencyID, int requestID);
	
	public native int userPasswordUpdate(String brokerID, String userID, String oldPassword, String newPassword, int requestID);
	
	/**
	 * 查询最大报单数量请求		-虽然ReqQueryMaxOrderVolume可以查询可开，但是交易核心在计算的时候是没有算手续费的，所以不完全准；如果需要精确结果的，建议自行计算。另外，可平的查询是已经排除了冻结持仓的。
	 * @param BrokerID		经纪公司代码
	 * @param InvestorID	投资者代码
	 * @param InstrumentID	合约代码
	 * @param Direction		买卖方向
	 * @param OffsetFlag	开平标志
	 * @param HedgeFlag		投机套保标志
	 * @param MaxVolume		最大允许报单数量
	 * @param ExchangeID	交易所代码
	 * @param InvestUnitID	投资单元代码
	 * @param requestID		请求ID
	 * @return
	 */
	public native int queryMaxOrderVolume(String brokerID, String investorID, String instrumentID, char direction, char offsetFlag, String hedgeFlag, int maxVolume, 
			String exchangeID, String investUnitID, int requestID);
	
	/**
	 * 请求查询投资者结算结果	-可以查询当天或历史结算单，也可以查询月结算单，但是前提是CTP柜台生成了相应的日或月结算单。
	 * @param brokerID;		经纪公司代码
	 * @param investorID;	投资者代码
	 * @param tradingDay;	交易日
	 * @param accountID;	投资者帐号
	 * @param currencyID;	币种代码
	 * @param requestID		请求ID
	 * @return
	 */
	public native int qrySettlementInfo(String brokerID, String investorID, String tradingDay, String accountID, String currencyID, int requestID);
	
	/**
	 * 投资者结算结果确认		在开始每日交易前都需要先确认上一日结算单，只需要确认一次。
	 * @param brokerID		经纪公司代码
	 * @param investorID	投资者代码
	 * @param confirmDate	确认日期
	 * @param confirmTime	确认时间
	 * @param settlementID	结算编号
	 * @param accountID		投资者帐号
	 * @param currencyID	币种代码
	 * @param requestID		请求ID
	 * @return
	 */
	public native int SettlementInfoConfirm(String brokerID, String investorID, String confirmDate, String confirmTime, int settlementID, String accountID, String currencyID, int requestID);
	
	/**
	 * 查询结算信息确认
	 * @param brokerID		经纪公司代码
	 * @param investorID	投资者代码
	 * @param accountID		投资者帐号
	 * @param currencyID	币种代码
	 * @param requestID		请求ID
	 * @return
	 */
	public native int qrySettlementInfoConfirm(String brokerID, String investorID, String accountID, String currencyID, int requestID);
	
	/**
	 * 查询报单
	 * @param BrokerID				经纪公司代码
	 * @param InvestorID			投资者代码
	 * @param InstrumentID			合约代码
	 * @param ExchangeID			交易所代码
	 * @param OrderSysID			报单编号
	 * @param InsertTimeStart		开始时间
	 * @param InsertTimeEnd			结束时间
	 * @param InvestUnitID			投资单元代码
	 * @param requestID				请求ID
	 * @return
	 */
	public native int qryOrder(String BrokerID, String InvestorID, String InstrumentID, String ExchangeID, String OrderSysID, String InsertTimeStart, String InsertTimeEnd, 
			String InvestUnitID, int requestID);
	
	/**
	 * 查询成交
	 * @param BrokerID				经纪公司代码
	 * @param InvestorID			投资者代码
	 * @param InstrumentID			合约代码
	 * @param ExchangeID			交易所代码
	 * @param TradeID				成交编号
	 * @param InsertTimeStart		开始时间
	 * @param InsertTimeEnd			结束时间
	 * @param InvestUnitID			投资单元代码
	 * @param requestID				请求ID
	 * @return
	 */
	public native int qryTrade(String brokerID, String investorID, String instrumentID, String exchangeID, String tradeID, String insertTimeStart, String insertTimeEnd, 
			String investUnitID, int requestID);
	
	/**
	 * 查询持仓汇总
	 * @param BrokerID				经纪公司代码
	 * @param InvestorID			投资者代码
	 * @param InstrumentID			合约代码
	 * @param ExchangeID			交易所代码
	 * @param InvestUnitID			投资单元代码
	 * @param requestID				请求ID
	 * @return
	 */
	public native int qryInvestorPosition(String brokerID, String investorID, String instrumentID, String exchangeID, String investUnitID, int requestID);
	
	/**
	 * 查询投资者持仓明细
	 * @param BrokerID				经纪公司代码
	 * @param InvestorID			投资者代码
	 * @param InstrumentID			合约代码
	 * @param ExchangeID			交易所代码
	 * @param InvestUnitID			投资单元代码
	 * @param requestID				请求ID
	 * @return
	 */
	public native int qryInvestorPositionDetail(String brokerID, String investorID, String instrumentID, String exchangeID, String investUnitID, int requestID);
	
	/**
	 * 查询资金账户
	 * @param BrokerID				经纪公司代码
	 * @param InvestorID			投资者代码
	 * @param currencyID			币种代码
	 * @param bizType				业务类型
	 * @param accountID				投资者帐号
	 * @param requestID				请求ID
	 * @return
	 */
	public native int qryTradingAccount(String BrokerID, String InvestorID, String currencyID, String bizType, String accountID, int requestID);
	
	/**
	 * 查询资金账户
	 * @param BrokerID				经纪公司代码
	 * @param InvestorID			投资者代码
	 * @param requestID				请求ID
	 * @return
	 */
	public native int qryInvestor(String BrokerID, String InvestorID, int requestID);
	
	/**
	 * 查询成交
	 * @param BrokerID				经纪公司代码
	 * @param InvestorID			投资者代码
	 * @param ExchangeID			交易所代码
	 * @param clientID				客户代码
	 * @param clientIDType			交易编码类型
	 * @param InvestUnitID			投资单元代码
	 * @param requestID				请求ID
	 * @return
	 */
	public native int qryTradingCode(String BrokerID, String InvestorID, String ExchangeID, String clientID, String clientIDType, String investUnitID, int requestID);
	
	/**
	 * 查询合约保证金率		-如果InstrumentID填空，则返回持仓对应的合约保证金率，否则返回相应InstrumentID的保证金率。目前无法通过一次查询得到所有合约保证金率，如果要查询所有，则需要通过多次查询得到。
	 * @param BrokerID		经纪公司代码
	 * @param InvestorID	投资者代码
	 * @param InstrumentID	合约代码
	 * @param HedgeFlag		投机套保标志
	 * @param ExchangeID	交易所代码
	 * @param InvestUnitID	投资单元代码
	 * @param requestID		请求ID
	 * @return
	 */	
	public native int qryInstrumentMarginRate(String brokerID, String investorID, String instrumentID, String hedgeFlag, String exchangeID, String investUnitID, int requestID);
	
	/**
	 * 查询合约手续费率		-如果InstrumentID填空，则返回持仓对应的合约手续费率。目前无法通过一次查询得到所有合约手续费率，如果要查询所有，则需要通过多次查询得到。
	 * @param brokerID		经纪公司代码
	 * @param investorID	投资者代码
	 * @param instrumentID	合约代码
	 * @param exchangeID	交易所代码
	 * @param investUnitID	投资单元代码
	 * @param requestID		请求ID
	 * @return
	 */	
	public native int qryInstrumentCommissionRate(String brokerID, String investorID, String instrumentID, String exchangeID, String investUnitID, int requestID);
	
	//////////////////////////////////////////////////////////////////////////////////
	/**
	 * 查询交易所
	 * @param exchangeID	交易所代码
	 * @param requestID		请求ID
	 * @return
	 */
	public native int qryExchange(String brokerID, String investorID, String exchangeID, int requestID);
	
	/**
	 * 查询产品
	 * @param productID		产品代码
	 * @param productClass	产品类型
	 * @param exchangeID	交易所代码
	 * @param requestID		请求ID
	 * @return
	 */
	public native int qryProduct(String brokerID, String investorID, String productID, String productClass,String exchangeID, int requestID);
	
	/**
	 * 查询合约
	 * @param instrumentID		合约代码
	 * @param exchangeID		交易所代码
	 * @param exchangeInstID	合约在交易所的代码
	 * @param productID			产品代码
	 * @param requestID			请求ID
	 * @return
	 */
	public native int qryInstrument(String brokerID, String investorID, String instrumentID, String exchangeID, String exchangeInstID, String productID, int requestID);
	
	/**
	 * 查询转帐银行
	 * @param bankID		银行代码
	 * @param bankBrchID	银行分中心代码
	 * @param requestID		请求ID
	 * @return
	 */
	public native int qryTransferBank(String brokerID, String investorID, String bankID, String bankBrchID, int requestID);
	
	/**
	 * 查询客户通知
	 * @param brokerID		经纪公司代码
	 * @param requestID		请求ID
	 * @return
	 */
	public native int qryNotice(String brokerID, String investorID, int requestID);
	
	/**
	 * 查询交易所保证金率
	 * @param brokerID			经纪公司代码
	 * @param instrumentID		合约代码
	 * @param hedgeFlag			投机套保标志
	 * @param exchangeID		交易所代码
	 * @param requestID			请求ID
	 * @return
	 */
	public native int qryExchangeMarginRate(String brokerID, String investorID, String instrumentID, String hedgeFlag, String exchangeID, int requestID);
	
	/**
	 * 查询交易所调整保证金率
	 * @param brokerID			经纪公司代码
	 * @param instrumentID		合约代码
	 * @param hedgeFlag			投机套保标志
	 * @param requestID			请求ID
	 * @return
	 */
	public native int qryExchangeMarginRateAdjust(String brokerID, String investorID, String instrumentID, String hedgeFlag, int requestID);
	///////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 查询投资单元
	 * @param brokerID			经纪公司代码
	 * @param investorID		投资者代码
	 * @param investUnitID		投资单元代码
	 * @param requestID			请求ID
	 * @return
	 */
	public native int qryInvestUnit(String brokerID, String investorID, String investUnitID, int requestID);
	
	/**
	 * 查询转帐流水
	 * @param brokerID			经纪公司代码
	 * @param accountID			投资者帐号
	 * @param bankID			银行编码
	 * @param CurrencyID		币种代码
	 * @param requestID			
	 * @return
	 */
	public native int qryTransferSerial(String brokerID, String accountID, String bankID, String CurrencyID, int requestID);
	
	/**
	 * 查询银期签约关系
	 * @param brokerID			经纪公司代码
	 * @param accountID			投资者帐号
	 * @param bankID			银行编码
	 * @param bankBranchID		银行分支机构编码
	 * @param CurrencyID		币种代码
	 * @param requestID			请求ID
	 * @return
	 */
	public native int qryAccountregister(String brokerID, String accountID, String bankID, String bankBranchID, String CurrencyID, int requestID);
	
	/**
	 * 查询签约银行
	 * @param brokerID		经纪公司代码
	 * @param bankID		银行编码
	 * @param bankBrchID	银行分中心代码
	 * @param requestID		请求ID
	 * @return
	 */
	public native int qryContractBank(String brokerID, String investorID, String bankID, String bankBrchID, int requestID);////////////////////////////////
	
	/**
	 * 查询交易通知
	 * @param brokerID			经纪公司代码
	 * @param investorID		投资者代码
	 * @param investUnitID		投资单元代码
	 * @param requestID			请求ID
	 * @return
	 */
	public native int qryTradingNotice(String brokerID, String investorID, String investUnitID, int requestID);
	
	/**
	 * 
	 * @param brokerID			经纪公司代码
	 * @param investorID		投资者代码
	 * @param instrumentID		合约代码
	 * @param requestID			请求ID
	 * @return
	 */
	public native int qryInstrumentOrderCommRate(String brokerID, String investorID, String instrumentID, int requestID);
	
	/**
	 * 期货发起银行资金转期货请求
	 * @param req			请求
	 * @param requestID		请求ID
	 * @return
	 */
	public native int fromBankToFutureByFuture(String tradeCode, String bankID, String bankBranchID, String brokerID, String brokerBranchID, String tradeDate, String tradeTime, String bankSerial, String tradingDay, 
			int plateSerial, String lastFragment, int sessionID, String customerName, String idCardType, String identifiedCardNo, String custType, String bankAccount, String bankPassWord, String accountID, 
			String password, int installID, int futureSerial, String userID, String verifyCertNoFlag, String currencyID, double tradeAmount, double futureFetchAmount, String feePayFlag, 
			double custFee, double brokerFee, String message, String digest, String bankAccType, String deviceID, String bankSecuAccType, String brokerIDByBank, String bankSecuAcc, String bankPwdFlag, 
			String secuPwdFlag, String operNo, int TID, String transferStatus, String longCustomerName, int requestID);
	
	/**
	 * 期货发起期货资金转银行请求
	 * @param req			请求
	 * @param requestID		请求ID
	 * @return
	 */
	public native int fromFutureToBankByFuture(String tradeCode, String bankID, String bankBranchID, String brokerID, String brokerBranchID, String tradeDate, String tradeTime, String bankSerial, String tradingDay, 
			int plateSerial, String lastFragment, int sessionID, String customerName, String idCardType, String identifiedCardNo, String custType, String bankAccount, String bankPassWord, String accountID, 
			String password, int installID, int futureSerial, String userID, String verifyCertNoFlag, String currencyID, double tradeAmount, double futureFetchAmount, String feePayFlag, 
			double custFee, double brokerFee, String message, String digest, String bankAccType, String deviceID, String bankSecuAccType, String brokerIDByBank, String bankSecuAcc, String bankPwdFlag, 
			String secuPwdFlag, String operNo, int TID, String transferStatus, String longCustomerName, int requestID);
	
	/**
	 * 查询银行余额
	 * @param req			请求
	 * @param requestID		请求ID
	 * @return
	 */
	public native int queryBankAccountMoneyByFuture(String tradeCode, String bankID, String bankBranchID, String brokerID, String brokerBranchID, String tradeDate, String tradeTime, String bankSerial, String tradingDay,
			int plateSerial, String lastFragment, int sessionID, String customerName, String idCardType, String identifiedCardNo, String custType, String bankAccount, String bankPassWord, String accountID, 
			String password, int installID, int futureSerial, String userID, String verifyCertNoFlag, String currencyID, String digest, String bankAccType, String deviceID, String bankSecuAccType, 
			String brokerIDByBank, String bankSecuAcc, String bankPwdFlag, String secuPwdFlag, String operNo, int TID, String longCustomerName, int requestID);

}
