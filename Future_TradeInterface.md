## CTP转发接口

#### 登录
```
POST http://{{host}}/userLogin
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`TradingDay`|string|0|交易日||
|`BrokerID`|string|1|经纪公司代码|0034|
|`UserID`|string|1|用户代码|56002085|
|`Password`|string|1|密码|yaypgigi820526|
|`UserProductInfo`|string|0|客户端的产品信息，如软件开发商、版本号等||
|`InterfaceProductInfo`|string|0|接口端产品信息||
|`ProtocolInfo`|string|0|协议信息||
|`MacAddress`|string|0|Mac地址||
|`OneTimePassword`|string|0|动态密码，如果期货公司启用了动态口令，客户会有一个电子口令牌，把生成的值，输入到登录报文里的OneTimePassword(动态密码)这个字段中去即可||
|`ClientIPAddress`|string|0|终端IP地址||
|`LoginRemark`|string|0|登录备注||
|`RequestID`|integer|1|请求ID|1|
|`Front`|string|0|||

> 详细说明
    
<p>CTP账户登录协议，与CTP的API不完全一样，这里会同步返回登录结果</p>

> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|number|返回码，0：登录成功|
|`message`|string|返回错误消息|
#### 用户口令更新请求
```
POST http://{{host}}/UserPasswordUpdate
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`UserID`|string|1|用户代码||
|`OldPassword`|string|1|原来的口令||
|`NewPassword`|string|1|新的口令||
|`RequestID`|integer|1|请求ID||

> 详细说明
    
<p>修改密码协议，对应CTP修改密码API：ReqUserPasswordUpdate，同步返回API调用结果，异步获取修改结果</p>

> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|string|返回码|
|`message`|string|错误消息|
#### 资金账户口令更新请求
```
POST http://{{host}}/TradingAccountPasswordUpdate
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`AccountID`|string|1|投资者帐号||
|`OldPassword`|string|1|原来的口令||
|`NewPassword`|string|1|新的口令||
|`CurrencyID`|string|1|币种代码，一般填"CNY"||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|number|返回码|
|`message`|string|错误信息|
#### 报单录入请求
```
POST http://{{host}}/OrderInsert
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`InvestorID`|string|1|投资者代码||
|`InstrumentID`|string|1|合约代码||
|`OrderRef`|string|1|报单引用，即本地订单号，必填||
|`UserID`|string|1|用户代码||
|`OrderPriceType`|string|1|报单价格条件||
|`Direction`|string|1|买卖方向||
|`CombOffsetFlag`|string|1|组合开平标志||
|`CombHedgeFlag`|string|1|组合投机套保标志，一般客户下单填 1:"投机"||
|`LimitPrice`|number|1|价格||
|`VolumeTotalOriginal`|number|1|数量，必填||
|`TimeCondition`|string|1|有效期类型||
|`GTDDate`|string|0|GTD日期||
|`VolumeCondition`|string|1|成交量类型||
|`MinVolume`|number|1|最小成交量||
|`ContingentCondition`|string|1|触发条件||
|`StopPrice`|number|1|止损价||
|`ForceCloseReason`|string|1|强平原因||
|`IsAutoSuspend`|number|1|自动挂起标志，必填，一般传0，CTP用来确定客户端断线时，委托是否自动撤销||
|`BusinessUnit`|string|0|业务单元||
|`RequestID`|integer|1|请求ID||
|`UserForceClose`|number|0|用户强平标志||
|`IsSwapOrder`|number|1|互换单标志||
|`ExchangeID`|string|1|交易所代码||
|`InvestUnitID`|string|0|投资单元代码||
|`AccountID`|string|0|投资者帐号||
|`CurrencyID`|string|0|币种代码，一般填"CNY"||
|`ClientID`|string|0|交易编码||
|`IPAddress`|string|0|IP地址||
|`MacAddress`|string|0|Mac地址||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|number|返回码|
|`message`|string|错误信息|
#### 报单操作请求
```
POST http://{{host}}/OrderAction
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`InvestorID`|string|1|投资者代码||
|`OrderActionRef`|number|0|本次撤单委托的本地委托号||
|`OrderRef`|string|1|报单引用，要撤的订单的本地订单号||
|`RequestID`|integer|1|请求ID||
|`FrontID`|number|1|对应要撤销的那笔报单的前置编号||
|`SessionID`|number|1|对应要撤销的那笔报单的会话编号||
|`ExchangeID`|string|1|对应要撤销的那笔报单的交易所ID||
|`OrderSysID`|string|1|对应要撤销的那笔报单的服务端订单号(CTP服务端的订单号，从委托响应或委托查询结果中获取并记录)||
|`ActionFlag`|string|1|操作标志，只支持删除，不支持修改||
|`LimitPrice`|double|0|价格||
|`VolumeChange`|integer|0|数量变化||
|`UserID`|string|1|用户代码||
|`InstrumentID`|string|1|合约代码||
|`InvestUnitID`|string|0|投资单元代码||
|`IPAddress`|string|0|客户端本地IP地址||
|`MacAddress`|string|0|客户端本地MAC地址||

> 详细说明
    
<p>根据CTP的API文档描述，本协议只用于撤单，不能修改订单</p><p>撤单有两种方式：</p><p>		1.使用OrderSysID撤单，示例：</p><p>			{</p><p>				"BrokerID":"9999",</p><p>				"InvestorID":"1000001",</p><p>				"UserID":"1000001",</p><p>				"OrderSysID":"131",</p><p>				"ExchangeID":"SHFE",</p><p>				"InstrumentID":"rb1809"</p><p>				"ActionFlag":0</p><p>			}</p><p>		2.第二种方法，使用FrontID+SessionID+OrderRef撤单，示例：</p><p>			{</p><p>				"BrokerID":"9999",</p><p>				"InvestorID":"1000001",</p><p>				"UserID":"1000001",</p><p>				"FrontID":"131",</p><p>				"SessionID":-788541,</p><p>				"OrderRef":"123",</p><p>				"ExchangeID":"SHFE",</p><p>				"InstrumentID":"rb1809"</p><p>				"ActionFlag":0</p><p>			}</p>

> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer|返回码|
|`message`|string|错误信息|
#### 查询最大报单数量请求
```
POST http://{{host}}/QueryMaxOrderVolume
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`InvestorID`|string|1|投资者代码||
|`InstrumentID`|string|1|合约代码||
|`Direction`|string|1|买卖方向||
|`OffsetFlag`|string|1|开平标志||
|`HedgeFlag`|string|1|投机套保标志||
|`MaxVolume`|number|0|最大允许报单数量||
|`ExchangeID`|string|0|交易所代码||
|`InvestUnitID`|string|0|投资单元代码||
|`RequestID`|integer|1|请求ID||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|number|返回码|
|`message`|string|错误信息|
#### 查询投资者结算结果
```
POST http://{{host}}/QrySettlementInfo
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`InvestorID`|string|1|投资者代码||
|`TradingDay`|string|1|交易日||
|`AccountID`|string|0|投资者帐号||
|`CurrencyID`|string|0|币种代码，一般填"CNY"||
|`RequestID`|integer|1|请求ID||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
#### 投资者结算结果确认
```
POST http://{{host}}/SettlementInfoConfirm
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`InvestorID`|string|1|投资者代码||
|`ConfirmDate`|string|0|确认日期||
|`ConfirmTime`|string|0|确认时间||
|`SettlementID`|number|0|结算编号||
|`AccountID`|string|0|投资者帐号||
|`CurrencyID`|string|0|币种代码，一般填"CNY"||
|`RequestID`|integer|1|请求ID||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
#### 查询结算信息确认
```
POST http://{{host}}/QrySettlementInfoConfirm
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`InvestorID`|string|1|投资者代码||
|`AccountID`|string|0|投资者帐号||
|`CurrencyID`|string|0|币种代码，一般填"CNY"||
|`RequestID`|integer|1|请求ID||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
#### 查询报单
```
POST http://{{host}}/QryOrder
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`InvestorID`|string|1|投资者代码||
|`InstrumentID`|string|0|合约代码||
|`ExchangeID`|string|0|交易所代码||
|`OrderSysID`|string|0|CTP服务端订单号||
|`InsertTimeStart`|string|0|开始时间|20190215|
|`InsertTimeEnd`|string|0|结束时间|20190216|
|`InvestUnitID`|string|0|投资单元代码||
|`RequestID`|integer|1|请求ID||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
#### 查询成交
```
POST http://{{host}}/QryTrade
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`InvestorID`|string|1|投资者代码||
|`InstrumentID`|string|0|合约代码||
|`ExchangeID`|string|0|交易所代码||
|`TradeID`|string|0|成交编号||
|`TradeTimeStart`|string|0|开始时间|20190215|
|`TradeTimeEnd`|string|0|结束时间|20190216|
|`InvestUnitID`|string|0|投资单元代码||
|`RequestID`|integer|1|请求ID||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
#### 查询投资者持仓
```
POST http://{{host}}/QryInvestorPosition
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`InvestorID`|string|1|投资者代码||
|`InstrumentID`|string|0|合约代码||
|`ExchangeID`|string|0|交易所代码||
|`InvestUnitID`|string|0|投资单元代码||
|`RequestID`|integer|1|请求ID||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
#### 查询投资者持仓明细
```
POST http://{{host}}/QryInvestorPositionDetail
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`InvestorID`|string|1|投资者代码||
|`InstrumentID`|string|0|合约代码||
|`ExchangeID`|string|0|交易所代码||
|`InvestUnitID`|string|0|投资单元代码||
|`RequestID`|integer|1|请求ID||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
#### 查询资金账户
```
POST http://{{host}}/QryTradingAccount
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`InvestorID`|string|1|投资者代码||
|`CurrencyID`|string|1|币种代码，一般填"CNY"||
|`BizType`|string|0|业务类型||
|`AccountID`|string|0|投资者帐号||
|`RequestID`|integer|1|请求ID||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
#### 查询投资者
```
POST http://{{host}}/QryInvestor
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`InvestorID`|string|1|投资者代码||
|`RequestID`|integer|1|请求ID||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
#### 查询合约保证金率
```
POST http://{{host}}/QryInstrumentMarginRate
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`InvestorID`|string|1|投资者代码||
|`InstrumentID`|string|1|合约代码||
|`HedgeFlag`|string|1|投机套保标志||
|`ExchangeID`|string|0|交易所代码||
|`InvestUnitID`|string|0|投资单元代码||
|`RequestID`|integer|1|请求ID||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
#### 查询合约手续费率
```
POST http://{{host}}/QryInstrumentCommissionRate
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`InvestorID`|string|1|投资者代码||
|`InstrumentID`|string|1|合约代码||
|`ExchangeID`|string|0|交易所代码||
|`InvestUnitID`|string|0|投资单元代码||
|`RequestID`|integer|1|请求ID||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
#### 查询合约报单手续费率
```
POST http://{{host}}/QryInstrumentOrderCommRate
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`InvestorID`|string|1|投资者代码||
|`InstrumentID`|string|1|合约代码||
|`RequestID`|integer|1|请求ID||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
#### 查询交易编码
```
POST http://{{host}}/QryTradingCode
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`InvestorID`|string|1|投资者代码||
|`ExchangeID`|string|0|交易所代码||
|`ClientID`|string|0|交易编码||
|`ClientIDType`|string|0|交易编码类型||
|`InvestUnitID`|string|0|投资单元代码||
|`RequestID`|integer|1|请求ID||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
#### 查询交易所
```
POST http://{{host}}/QryExchange
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|期货公司代码||
|`InvestorID`|string|1|投资者编号||
|`ExchangeID`|string|0|交易所代码||
|`RequestID`|integer|1|请求ID||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
#### 查询产品
```
POST http://{{host}}/QryProduct
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|||
|`InvestorID`|string|1|||
|`ProductID`|string|0|产品代码||
|`ProductClass`|string|0|产品类型||
|`ExchangeID`|string|0|交易所代码||
|`RequestID`|integer|1|请求ID||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
#### 查询合约
```
POST http://{{host}}/QryInstrument
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|期货公司代码||
|`InvestorID`|string|1|投资者代码||
|`InstrumentID`|string|0|合约代码||
|`ExchangeID`|string|0|交易所代码||
|`ExchangeInstID`|string|0|合约在交易所的代码||
|`ProductID`|string|0|产品代码||
|`RequestID`|number|1|||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
#### 查询转帐银行
```
POST http://{{host}}/QryTransferBank
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|||
|`InvestorID`|string|1|||
|`BankID`|string|0|银行代码||
|`BankBrchID`|string|0|银行分中心代码||
|`RequestID`|number|1|||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
#### 查询客户通知
```
POST http://{{host}}/QryNotice
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`InvestorID`|string|1|投资者代码||
|`RequestID`|integer|1|请求ID||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
#### 查询交易所保证金率
```
POST http://{{host}}/QryExchangeMarginRate
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|||
|`InvestorID`|string|1|投资者代码||
|`InstrumentID`|string|1|||
|`HedgeFlag`|string|1|||
|`ExchangeID`|string|1|||
|`RequestID`|number|1|||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|number||
|`message`|string||
#### 查询交易所调整保证金率
```
POST http://{{host}}/QryExchangeMarginRateAdjust
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`InvestorID`|string|1|||
|`InstrumentID`|string|1|合约代码||
|`HedgeFlag`|string|1|投机套保标志||
|`RequestID`|integer|1|请求ID||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
#### 查询投资单元
```
POST http://{{host}}/QryInvestUnit
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`InvestorID`|string|1|投资者代码||
|`InvestUnitID`|string|0|投资单元代码||
|`RequestID`|integer|1|请求ID||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
#### 查询转帐流水
```
POST http://{{host}}/QryTransferSerial
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`AccountID`|string|1|投资者帐号||
|`BankID`|string|0|银行代码||
|`CurrencyID`|string|0|币种代码，一般填"CNY"||
|`RequestID`|integer|1|请求ID||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|number||
|`message`|string||
#### 查询银期签约关系
```
POST http://{{host}}/QryAccountregister
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`AccountID`|string|1|投资者帐号||
|`BankID`|string|1|银行代码||
|`BankBranchID`|string|0|银行分支机构编码||
|`CurrencyID`|string|0|币种代码，一般填"CNY"||
|`RequestID`|integer|1|请求ID||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|number||
|`message`|string||
#### 查询签约银行
```
POST http://{{host}}/QryContractBank
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`InvestorID`|string|1|投资者代码||
|`BankID`|string|0|银行代码||
|`BankBrchID`|string|0|银行分中心代码||
|`RequestID`|integer|1|请求ID||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
#### 查询交易通知
```
POST http://{{host}}/QryTradingNotice
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`BrokerID`|string|1|经纪公司代码||
|`InvestorID`|string|0|投资者代码||
|`InvestUnitID`|string|0|投资单元代码||
|`RequestID`|integer|1|请求ID||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|number||
|`message`|string||
#### 期货发起期货资金转银行
```
POST http://{{host}}/FromFutureToBankByFuture
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`TradeCode`|string|0|业务功能码|202001|
|`BankID`|string|1|银行代码||
|`BankBranchID`|string|1|银行分支机构编码||
|`BrokerID`|string|1|经纪公司代码||
|`BrokerBranchID`|string|0|期商分支机构代码||
|`TradeDate`|string|0|交易日期||
|`TradeTime`|string|0|交易时间||
|`BankSerial`|string|0|银行流水号||
|`TradingDay`|string|0|交易系统日期||
|`PlateSerial`|integer|0|银期平台消息流水号||
|`LastFragment`|string|1|最后分片标志||
|`SessionID`|integer|0|会话号||
|`CustomerName`|string|0|客户姓名||
|`IdCardType`|string|1|证件类型||
|`IdentifiedCardNo`|string|0|证件号码||
|`CustType`|string|1|客户类型||
|`BankAccount`|string|1|银行帐号||
|`BankPassWord`|string|0|银行密码||
|`AccountID`|string|1|投资者帐号||
|`Password`|string|1|期货密码||
|`InstallID`|integer|0|安装编号||
|`FutureSerial`|integer|1|期货公司流水号||
|`UserID`|string|1|用户代码||
|`VerifyCertNoFlag`|string|1|验证客户证件号码标志||
|`CurrencyID`|string|1|币种代码，一般填"CNY"||
|`TradeAmount`|double|1|转帐金额||
|`FutureFetchAmount`|double|1|期货可取金额||
|`FeePayFlag`|string|0|费用支付标志||
|`CustFee`|double|1|应收客户费用||
|`BrokerFee`|double|1|应收期货公司费用||
|`Message`|string|0|发送方给接收方的消息||
|`Digest`|string|0|摘要||
|`BankAccType`|string|0|银行帐号类型||
|`DeviceID`|string|0|渠道标志||
|`BankSecuAccType`|string|0|期货单位帐号类型||
|`BrokerIDByBank`|string|0|期货公司银行编码||
|`BankSecuAcc`|string|0|期货单位帐号||
|`BankPwdFlag`|string|0|银行密码标志||
|`SecuPwdFlag`|string|1|期货资金密码核对标志||
|`OperNo`|string|0|交易柜员||
|`RequestID`|integer|1|请求ID||
|`TID`|integer|1|交易ID||
|`TransferStatus`|string|1|转账交易状态||
|`LongCustomerName`|string|1|长客户姓名||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
#### 期货发起银行资金转期货
```
POST http://{{host}}/FromBankToFutureByFuture
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`TradeCode`|string|1|业务功能码|202001|
|`BankID`|string|1|银行代码||
|`BankBranchID`|string|1|银行分支机构编码||
|`BrokerID`|string|1|经纪公司代码||
|`BrokerBranchID`|string|0|期商分支机构代码||
|`TradeDate`|string|0|交易日期||
|`TradeTime`|string|0|交易时间||
|`BankSerial`|string|0|银行流水号||
|`TradingDay`|string|0|交易日||
|`PlateSerial`|integer|0|银期平台消息流水号||
|`LastFragment`|string|1|最后分片标志||
|`SessionID`|integer|1|会话号||
|`CustomerName`|string|0|客户姓名||
|`IdCardType`|string|1|证件类型||
|`IdentifiedCardNo`|string|1|证件号码||
|`CustType`|string|1|客户类型||
|`BankAccount`|string|1|银行帐号||
|`BankPassWord`|string|0|银行密码||
|`AccountID`|string|1|投资者帐号||
|`Password`|string|1|||
|`InstallID`|integer|0|安装编号||
|`FutureSerial`|integer|0|期货公司流水号||
|`UserID`|string|1|用户代码||
|`VerifyCertNoFlag`|string|1|验证客户证件号码标志||
|`CurrencyID`|string|1|币种代码，一般填"CNY"||
|`TradeAmount`|double|1|转帐金额||
|`FutureFetchAmount`|double|1|期货可取金额||
|`FeePayFlag`|string|0|费用支付标志||
|`CustFee`|double|1|应收客户费用||
|`BrokerFee`|double|1|应收期货公司费用||
|`Message`|string|0|发送方给接收方的消息||
|`Digest`|string|0|摘要||
|`BankAccType`|string|0|银行帐号类型||
|`DeviceID`|string|0|渠道标志||
|`BankSecuAccType`|string|0|期货单位帐号类型||
|`BrokerIDByBank`|string|0|期货公司银行编码||
|`BankSecuAcc`|string|0|期货单位帐号||
|`BankPwdFlag`|string|0|银行密码标志||
|`SecuPwdFlag`|string|0|期货资金密码核对标志||
|`OperNo`|string|0|交易柜员||
|`RequestID`|integer|1|请求ID||
|`TID`|integer|1|交易ID||
|`TransferStatus`|string|0|转账交易状态||
|`LongCustomerName`|string|0|长客户姓名||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
#### 期货发起查询银行余额请求
```
POST http://{{host}}/QueryBankAccountMoneyByFuture
```



>Body

```json


```
|参数名|类型|必需|描述|示例|
|---------|-------|-------------|-------|--|
|`TradeCode`|string|1|业务功能码|202001|
|`BankID`|string|1|银行代码||
|`BankBranchID`|string|1|银行分支机构编码||
|`BrokerID`|string|1|经纪公司代码||
|`BrokerBranchID`|string|0|期商分支机构代码||
|`TradeDate`|string|0|交易日期||
|`TradeTime`|string|0|交易时间||
|`BankSerial`|string|0|银行流水号||
|`TradingDay`|string|0|交易日||
|`PlateSerial`|integer|0|银期平台消息流水号||
|`LastFragment`|string|1|最后分片标志||
|`SessionID`|integer|0|会话号||
|`CustomerName`|string|0|客户姓名||
|`IdCardType`|string|0|证件类型||
|`IdentifiedCardNo`|string|0|证件号码||
|`CustType`|string|0|客户类型||
|`BankAccount`|string|0|银行帐号||
|`BankPassWord`|string|0|银行密码||
|`AccountID`|string|1|投资者帐号||
|`Password`|string|1|期货密码||
|`FutureSerial`|integer|0|期货公司流水号||
|`InstallID`|integer|0|安装编号||
|`UserID`|string|1|用户代码||
|`VerifyCertNoFlag`|string|0|验证客户证件号码标志||
|`CurrencyID`|string|0|币种代码，一般填"CNY"||
|`Digest`|string|0|摘要||
|`BankAccType`|string|0|银行帐号类型||
|`DeviceID`|string|0|渠道标志||
|`BankSecuAccType`|string|0|期货单位帐号类型||
|`BrokerIDByBank`|string|0|期货公司银行编码||
|`BankSecuAcc`|string|0|期货单位帐号||
|`BankPwdFlag`|string|0|银行密码标志||
|`SecuPwdFlag`|string|0|期货资金密码核对标志||
|`OperNo`|string|0|交易柜员||
|`RequestID`|integer|1|请求ID||
|`TID`|integer|1|交易ID||
|`LongCustomerName`|string|0|长客户姓名||


> 返回示例

```json


```
> 返回参数
    
|参数名|类型|描述|
|--|--|--|
|`retcode`|integer||
|`message`|string||
