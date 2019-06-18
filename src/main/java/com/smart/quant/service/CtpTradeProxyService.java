package com.smart.quant.service;

import com.smart.quant.model.BankTransferReq;
import com.smart.quant.model.OrderAction;
import com.smart.quant.model.OrderInsert;
import com.smart.quant.model.QueryBankAccountMoneyReq;
import com.smart.quant.model.Result;

public interface CtpTradeProxyService {
	/**
	 * 用户登录请求
	 * @param frontServer				
	 * @param brokerID					经纪公司代码
	 * @param userID					用户代码
	 * @param password					密码
	 * @param userProductInfo			用户端产品信息
	 * @param interfaceProductInfo		接口端产品信息
	 * @param protocolInfo				协议信息
	 * @param macAddress				Mac地址
	 * @param oneTimePassword			动态密码
	 * @param clientIPAddress			终端IP地址
	 * @param loginRemark				登录备注
	 * @param requestID					请求ID
	 * @return
	 */
	Result userLogin(String frontServer, String brokerID, String userID, String password, String userProductInfo, String interfaceProductInfo, String protocolInfo, 
			String macAddress, String oneTimePassword, String clientIPAddress, String loginRemark, int requestID);
	
	Result quotationUserLogin(String frontServer, String brokerID, String userID, String password, int requestID);
	/**
	 * 用户登出请求
	 * @param brokerID		经纪公司代码
	 * @param userID		用户代码
	 * @param requestID		请求ID
	 * @return
	 */
	Result userLogout(String brokerID, String userID, int requestID);
	
	/**
	 * 用户口令变更
	 * @param brokerID			经纪公司代码
	 * @param userID			用户代码
	 * @param oldPassword		原来的口令
	 * @param newPassword		新的口令
	 * @param requestID			请求ID
	 * @return	
	 */
	Result userPasswordUpdate(String brokerID, String userID, String oldPassword, String newPassword, int requestID);
	
	/**
	 * 
	 * @param brokerID			经纪公司代码
	 * @param accountID			投资者帐号
	 * @param oldPassword		原来的口令
	 * @param newPassword		新的口令
	 * @param currencyID		币种代码
	 * @param requestID			请求ID
	 * @return
	 */
	Result tradingAccountPasswordUpdate(String brokerID, String accountID, String oldPassword, String newPassword, String currencyID, int requestID);
	
	/**
	 * 报单录入请求
	 * @param order		order entity
	 * @return
	 */
	Result orderInsert(String brokerID, String userID, OrderInsert order, int requestID);
	
	/**
	 * 报单操作请求
	 * @param order			order action entity
	 * @return
	 */
	Result orderAction(String brokerID, String userID, OrderAction order, int requestID);
	
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
	Result queryMaxOrderVolume(String brokerID, String investorID, String instrumentID, String direction, String offsetFlag, String hedgeFlag, Integer maxVolume, 
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
	Result qrySettlementInfo(String brokerID, String investorID, String tradingDay, String accountID, String currencyID, int requestID);
	
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
	Result SettlementInfoConfirm(String brokerID, String investorID, String confirmDate, String confirmTime, Integer settlementID, String accountID, String currencyID, int requestID);
	
	/**
	 * 查询结算信息确认
	 * @param brokerID		经纪公司代码
	 * @param investorID	投资者代码
	 * @param accountID		投资者帐号
	 * @param currencyID	币种代码
	 * @param requestID		请求ID
	 * @return
	 */
	Result qrySettlementInfoConfirm(String brokerID, String investorID, String accountID, String currencyID, int requestID);
	
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
	Result qryOrder(String BrokerID, String InvestorID, String InstrumentID, String ExchangeID, String OrderSysID, String InsertTimeStart, String InsertTimeEnd, 
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
	Result qryTrade(String brokerID, String investorID, String instrumentID, String exchangeID, String tradeID, String insertTimeStart, String insertTimeEnd, 
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
	Result qryInvestorPosition(String brokerID, String investorID, String instrumentID, String exchangeID, String investUnitID, int requestID);
	
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
	Result qryInvestorPositionDetail(String brokerID, String investorID, String instrumentID, String exchangeID, String investUnitID, int requestID);
	
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
	Result qryTradingAccount(String BrokerID, String InvestorID, String currencyID, String bizType, String accountID, int requestID);
	
	/**
	 * 查询投资者
	 * @param BrokerID				经纪公司代码
	 * @param InvestorID			投资者代码
	 * @param requestID				请求ID
	 * @return
	 */
	Result qryInvestor(String BrokerID, String InvestorID, int requestID);
	
	/**
	 * 查询交易编码
	 * @param BrokerID				经纪公司代码
	 * @param InvestorID			投资者代码
	 * @param ExchangeID			交易所代码
	 * @param clientID				客户代码
	 * @param clientIDType			交易编码类型
	 * @param InvestUnitID			投资单元代码
	 * @param requestID				请求ID
	 * @return
	 */
	Result qryTradingCode(String BrokerID, String InvestorID, String ExchangeID, String clientID, String clientIDType, String investUnitID, int requestID);
	
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
	Result qryInstrumentMarginRate(String brokerID, String investorID, String instrumentID, String hedgeFlag, String exchangeID, String investUnitID, int requestID);
	
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
	Result qryInstrumentCommissionRate(String brokerID, String investorID, String instrumentID, String exchangeID, String investUnitID, int requestID);
	
	/**
	 * 查询交易所
	 * @param exchangeID	交易所代码
	 * @param requestID		请求ID
	 * @return
	 */
	Result qryExchange(String brokerID, String investorID, String exchangeID, int requestID);
	
	/**
	 * 查询产品
	 * @param productID		产品代码
	 * @param productClass	产品类型
	 * @param exchangeID	交易所代码
	 * @param requestID		请求ID
	 * @return
	 */
	Result qryProduct(String brokerID, String investorID, String productID, String productClass,String exchangeID, int requestID);
	
	/**
	 * 查询合约
	 * @param instrumentID		合约代码
	 * @param exchangeID		交易所代码
	 * @param exchangeInstID	合约在交易所的代码
	 * @param productID			产品代码
	 * @param requestID			请求ID
	 * @return
	 */
	Result qryInstrument(String brokerID, String investorID, String instrumentID, String exchangeID, String exchangeInstID, String productID, int requestID);
	
	/**
	 * 查询转帐银行
	 * @param bankID		银行代码
	 * @param bankBrchID	银行分中心代码
	 * @param requestID		请求ID
	 * @return
	 */
	Result qryTransferBank(String brokerID, String investorID, String bankID, String bankBrchID, int requestID);
	
	/**
	 * 查询客户通知
	 * @param brokerID		经纪公司代码
	 * @param requestID		请求ID
	 * @return
	 */
	Result qryNotice(String brokerID, String investorID, int requestID);
	
	/**
	 * 查询交易所保证金率
	 * @param brokerID			经纪公司代码
	 * @param instrumentID		合约代码
	 * @param hedgeFlag			投机套保标志
	 * @param exchangeID		交易所代码
	 * @param requestID			请求ID
	 * @return
	 */
	Result qryExchangeMarginRate(String brokerID, String investorID, String instrumentID, String hedgeFlag, String exchangeID, int requestID);
	
	/**
	 * 查询交易所调整保证金率
	 * @param brokerID			经纪公司代码
	 * @param instrumentID		合约代码
	 * @param hedgeFlag			投机套保标志
	 * @param requestID			请求ID
	 * @return
	 */
	Result qryExchangeMarginRateAdjust(String brokerID, String investorID, String instrumentID, String hedgeFlag, int requestID);
	
	/**
	 * 查询投资单元
	 * @param brokerID			经纪公司代码
	 * @param investorID		投资者代码
	 * @param investUnitID		投资单元代码
	 * @param requestID			请求ID
	 * @return
	 */
	Result qryInvestUnit(String brokerID, String investorID, String investUnitID, int requestID);
	
	/**
	 * 查询转帐流水
	 * @param brokerID			经纪公司代码
	 * @param accountID			投资者帐号
	 * @param bankID			银行编码
	 * @param CurrencyID		币种代码
	 * @param requestID			
	 * @return
	 */
	Result qryTransferSerial(String brokerID, String accountID, String bankID, String CurrencyID, int requestID);
	
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
	Result qryAccountregister(String brokerID, String accountID, String bankID, String bankBranchID, String CurrencyID, int requestID);
	
	/**
	 * 查询签约银行
	 * @param brokerID		经纪公司代码
	 * @param bankID		银行编码
	 * @param bankBrchID	银行分中心代码
	 * @param requestID		请求ID
	 * @return
	 */
	Result qryContractBank(String brokerID, String investorID, String bankID, String bankBrchID, int requestID);
	
	/**
	 * 查询交易通知
	 * @param brokerID			经纪公司代码
	 * @param investorID		投资者代码
	 * @param investUnitID		投资单元代码
	 * @param requestID			请求ID
	 * @return
	 */
	Result qryTradingNotice(String brokerID, String investorID, String investUnitID, int requestID);
	
	/**
	 * 期货发起银行资金转期货请求
	 * @param req			请求
	 * @param requestID		请求ID
	 * @return
	 */
	Result fromBankToFutureByFuture(String brokerID, String investorID, BankTransferReq req, int requestID);
	
	/**
	 * 期货发起期货资金转银行请求
	 * @param req			请求
	 * @param requestID		请求ID
	 * @return
	 */
	Result fromFutureToBankByFuture(String brokerID, String investorID, BankTransferReq req, int requestID);
	
	/**
	 * 查询银行余额
	 * @param req			请求
	 * @param requestID		请求ID
	 * @return
	 */
	Result queryBankAccountMoneyByFuture(String brokerID, String investorID, QueryBankAccountMoneyReq req, int requestID);
	
	/**
	 * 
	 * @param brokerID			经纪公司代码
	 * @param investorID		投资者代码
	 * @param instrumentID		合约代码
	 * @param requestID			请求ID
	 * @return
	 */
	Result qryInstrumentOrderCommRate(String brokerID, String investorID, String instrumentID, int requestID);
	
	String getTradingDay(String brokerID, String userID);
}
