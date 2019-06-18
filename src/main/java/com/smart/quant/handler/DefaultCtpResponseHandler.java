package com.smart.quant.handler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smart.quant.cache.UserCache;
import com.smart.quant.model.CtpMessage;

@Component
public class DefaultCtpResponseHandler implements CtpResponseHandler {
	private static final Logger logger = LoggerFactory.getLogger(DefaultCtpResponseHandler.class);
	
	@Resource
	private UserCache userCache;
	@Override
	public boolean handleMessage(CtpMessage message) {
		if(message == null) {
			logger.error("ctp call back message error, message is null");
			return false;
		}
		String brokerId = message.getBroker();
		String userId = message.getAccount();
		if(StringUtils.isEmpty(brokerId) || StringUtils.isEmpty(userId)) {
			logger.error("ctp call back message error, brokerID=" + (brokerId != null ? brokerId : "null") + ",userId=" + (userId != null ? userId : "null"));
			return false;
		}
		String userKey = brokerId + "_" + userId;
		Set<WebSocketSession> sessionSet = userCache.getUserSessions(userKey);
		Set<WebSocketSession> invalidSession = new HashSet<WebSocketSession>();
		if(sessionSet != null && sessionSet.size() > 0) {
			sessionSet.forEach((WebSocketSession session) -> {
				if(session != null && session.isOpen()) {
					if(session.isOpen()) {
						sendResponse(session, message, invalidSession);
						logger.info("ctp response send completed");
					} else {
						invalidSession.add(session);
					}
				}
			});
		} else {
			logger.error("there is no invalid session, userKey = " + userKey);			
		}
		if(invalidSession.size() > 0) {
			sessionSet.removeAll(invalidSession);
			logger.info("invalid session count:" + invalidSession.size() + ",clear now");
		}
		return true;
	}

	
	private void sendResponse(WebSocketSession session, CtpMessage ctpMessage, Set<WebSocketSession> invalidSession) {
		int nRequestID = ctpMessage.getRequestID();
		String msgName = ctpMessage.getName();
		String message = ctpMessage.getMessage();
		JSON data = ctpMessage.getData();
		int retcode = ctpMessage.getRetcode();
		String account = ctpMessage.getAccount();
		JSONObject json = new JSONObject();
		json.put("name", msgName);
		json.put("account", account);
		json.put("retCode", retcode);
		json.put("message", message);
		json.put("data", data);
		json.put("requestID", nRequestID);
		try {
			session.sendMessage(new TextMessage(json.toJSONString()));
			logger.info("send websocket message completed");
		} catch (IOException e) {
			logger.error("send websocket message error", e);
			invalidSession.add(session);
		}
	}
}
