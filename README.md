
#项目说明
1.  基于java封装的国内期货交易接口(CTP)，将CTP的动态链接库接口封装为HTTP协议，方便各种语言，各种应用环境下，从本地/远程调用。
2.  CTP异步返回消息，通过WebSocket链接推送。
3.  Ctp接口的JNI封装使用Visual Studio 2015构建，运行时需要安装开发包：[Visual C++ Redistributable for Visual Studio 2015](https://www.microsoft.com/zh-CN/download/details.aspx?id=48145)
4.  封装后的Http协议见文件：Future_TradeInterface.md
