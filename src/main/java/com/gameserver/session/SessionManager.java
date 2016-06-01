package com.gameserver.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionManager {

	Logger logger = LoggerFactory.getLogger(SessionManager.class);

	Map<Long, Session> sessionCache = new HashMap<Long, Session>();
	Random random = new Random();

	public static SessionManager me = new SessionManager();

	private SessionManager() {

	}

	public void put(Session session) {
		sessionCache.put(session.getSessionId(), session);
		logger.debug("#sessionId: " + session.getSessionId() + " added.");
	}

	public void remove(long sessionId) {
		Session session = sessionCache.get(sessionId);
		if (session.getChannel().isOpen()) {
			session.getChannel().close();
		}
		sessionCache.remove(sessionId);
		logger.debug("#sessionId: " + session.getSessionId() + " removed.");
	}

	public long generateSessionId() {
		long one = random.nextLong();
		while (sessionCache.containsKey(one)) {
			one = random.nextLong();
		}
		return one;
	}
}
