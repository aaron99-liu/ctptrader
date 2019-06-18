package com.smart.quant.cache;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketSession;

@Component
public class UserCache {
	private static Logger logger = LoggerFactory.getLogger(UserCache.class);
	
	private Map<String, Set<WebSocketSession>> sessionMap = new HashMap<String, Set<WebSocketSession>>();
	
	public void addUserSession(String userKey, WebSocketSession session) {
		if(!StringUtils.isEmpty(userKey) && session != null) {
			Set<WebSocketSession> sessionSet = sessionMap.get(userKey);
			if(sessionSet == null) {
				sessionSet = new HashSet<WebSocketSession>();
				sessionMap.put(userKey, sessionSet);
			}
			sessionSet.add(session);
		}
	}
	
	public Set<WebSocketSession> getUserSessions(String userKey) {
		if(StringUtils.isEmpty(userKey)) {
			return null;
		}
		return this.sessionMap.get(userKey);
	}
}
