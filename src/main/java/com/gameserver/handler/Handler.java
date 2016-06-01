package com.gameserver.handler;

import com.gameserver.net.Message;

public interface Handler {

	void handle(Message request, Message response);
}
