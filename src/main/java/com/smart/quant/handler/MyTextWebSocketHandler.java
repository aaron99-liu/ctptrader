package com.smart.quant.handler;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smart.quant.cache.UserCache;

@Component
public class MyTextWebSocketHandler extends TextWebSocketHandler {
	private static Logger logger = LoggerFactory.getLogger(MyTextWebSocketHandler.class);
	
	private String initSucessResp = "{retcode: 0, message:\"\"}";
	
	@Resource
	private UserCache userCache;
	
	@Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
		String payLoad = message.getPayload();
		if(!StringUtils.isEmpty(payLoad)) {
			try {
				JSONObject json = JSON.parseObject(payLoad);
				String brokerId = json.getString("BrokerID");
				String userId = json.getString("UserID");
				if(!StringUtils.isEmpty(brokerId) && !StringUtils.isEmpty(userId)) {
					String userKey = brokerId + "_" + userId;
					userCache.addUserSession(userKey, session);
					session.sendMessage(new TextMessage(initSucessResp));
					logger.info("add session sucess, userKey=" + userKey);
				}
			} catch (Exception e) {
				logger.error("parse websocket message error", e);
			}
		}
    }	
	
}
