package com.gameserver.interceptor;

import java.util.Iterator;

import com.gameserver.ActionInvocation;
import com.gameserver.net.Message;

public interface Interceptor {

	void intercept(ActionInvocation ai, Iterator<Interceptor> interceptors, Message request, Message response);

}
