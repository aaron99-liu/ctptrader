package com.smart.quant.handler;

import com.smart.quant.model.CtpMessage;

public interface CtpResponseHandler {
	/**
	 * 消息处理接口 处理jni层返回的消息
	 * @param message	消息封装
	 * @return	true -继续由下一个handler处理当前消息，false -终止处理当前消息
	 */
	boolean handleMessage(CtpMessage message);
}
