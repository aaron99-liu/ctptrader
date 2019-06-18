package com.smart.quant.processor;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smart.quant.handler.CtpResponseHandler;
import com.smart.quant.handler.DefaultCtpResponseHandler;
import com.smart.quant.model.CtpMessage;

@Component
public class CtpCallbackProcessor {
	private static final Logger logger = LoggerFactory.getLogger(CtpCallbackProcessor.class);

	private static final ConcurrentLinkedQueue<CtpMessage> messageQueue = new ConcurrentLinkedQueue<CtpMessage>();
	private static final Object messageQueueReadSignel = new Object();
	private static final Object messageQueueWriteSignel = new Object();
	private static final Map<String, Map<Integer, CtpMessage>> ctpMessageMap = new ConcurrentHashMap<String, Map<Integer, CtpMessage>>();
	private static final List<CtpResponseHandler> handlerList = new LinkedList<CtpResponseHandler>();

	private static final Runnable messageConsumerTask = new Runnable() {

		@Override
		public void run() {
			while(true) {				
				CtpMessage msg = messageQueue.poll();
				if(msg == null) {
					waitSignal(messageQueueWriteSignel, 2000);
				} else {
					for(CtpResponseHandler handler : handlerList) {
						if(!handler.handleMessage(msg)) {
							break;	//handler return false, so break
						}
					}

				}
			}
		}
	};

	private static void waitSignal(Object signal, long timeout) {
		synchronized(signal) {
			try {										
				signal.wait(timeout);
			} catch (InterruptedException e) {
				logger.error("signal wait timeout", e);
			}
		}
	}

	private static void notifySignal(Object signal) {
		synchronized(signal) {									
			signal.notifyAll();
		}
	}

	public static void tradeMessageCallback(int nRequestID, String account, String msgName, String brokerID, int retcode, String message, String data, boolean isLast) {
		logger.info("message recv, name=[" + msgName + "], requestID=[" + nRequestID + "], msg=[" + message + "], isLast=" + isLast + ", data=" + data);
		JSONObject json = null;
		if(!StringUtils.isEmpty(data)) {			
			try {
				json = JSON.parseObject(data);
			} catch (Exception e) {
				logger.error("parse data json fail", e);
			}
		}
		Map<Integer, CtpMessage> messageMap = ctpMessageMap.get(account);
		if(messageMap == null) {
			messageMap = new ConcurrentHashMap<Integer, CtpMessage>();
			ctpMessageMap.put(account, messageMap);
		}
		if(isLast) {
			CtpMessage msg = messageMap.remove(nRequestID);
			if(msg != null) {
				//之前有其他消息，本条消息为最后一条
				JSON dataJson = msg.getData();
				if(json != null && dataJson instanceof JSONArray) {
					((JSONArray)dataJson).add(json);
				}
			} else {
				//之前没有其他消息，本条消息是最后一条
				msg = new CtpMessage(nRequestID, account, msgName, brokerID, retcode, message, json);
			}
			for(;;) {
			if(messageQueue.offer(msg)) {
				notifySignal(messageQueueWriteSignel);
				break;
			} else
				waitSignal(messageQueueReadSignel, 1000);
			}
		} else {
			//本条消息不是最后一条，先缓存起来
			CtpMessage msg = messageMap.get(nRequestID);
			if(msg != null) {
				JSON dataJson = msg.getData();
				if(dataJson instanceof JSONArray) {
					((JSONArray) dataJson).add(json);
				} else {
					logger.error("ctp message data is not json array, dataJson=" + dataJson == null ? "null" : dataJson.toJSONString());
				}
			} else {
				JSONArray jsonArray = new JSONArray();
				jsonArray.add(json);
				msg = new CtpMessage(nRequestID, account, msgName, brokerID, retcode, message, jsonArray);				
			}
			messageMap.put(nRequestID, msg);
		}
	}

	@Resource
	private DefaultCtpResponseHandler defaultCtpResponseHandler;
	@PostConstruct
	public void initConsumerTask() {
		handlerList.add(defaultCtpResponseHandler);
		new Thread(messageConsumerTask).start();
	}

}
