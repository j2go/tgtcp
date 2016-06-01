package com.gameserver.aop;

import java.util.Iterator;

import com.gameserver.handler.Handler;
import com.gameserver.net.Message;
import com.gameserver.util.SpringContainer;

public class Invocation {

	private Handler handler;
	private Message request;
	private Message response;

	public Message getRequest() {
		return request;
	}

	public Message getResponse() {
		return response;
	}

	private Iterator<Interceptor> iterator;

	public Invocation(int cmdId, Message request, Message response, Iterator<Interceptor> iterator) {
		super();
		handler = (Handler) SpringContainer.getInstance().getBeanById("handler" + cmdId);
		this.request = request;
		this.response = response;
		this.iterator = iterator;
	}

	public void invoke() {
		if (iterator.hasNext()) {
			Interceptor interceptor = iterator.next();
			interceptor.intercept(this);
		} else {
			handler.handle(request, response);
		}

	}
}
