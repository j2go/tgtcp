package com.gameserver;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gameserver.aop.Interceptor;
import com.gameserver.aop.Invocation;
import com.gameserver.net.Header;
import com.gameserver.net.Message;
import com.gameserver.session.Session;
import com.gameserver.session.SessionManager;
import com.gameserver.util.ErrorCode;

/**
 * 协议处理器 1.创建sessionID 2.接受客户端的消息进行转发
 * 
 * @author zhaohui
 *
 */
public class ServerHandler extends SimpleChannelHandler {

	private final static Logger logger = LoggerFactory.getLogger(ServerHandler.class);

	List<Interceptor> interceptors = new LinkedList<Interceptor>();

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		Long sessionID = (Long) ctx.getAttachment();
		if (sessionID == null) {
			sessionID = SessionManager.me.generateSessionId();
			Session session = new Session();
			session.setSessionId(sessionID);
			// TODO 处理 IP
			session.setChannel(e.getChannel());
			ctx.setAttachment(sessionID);
			SessionManager.me.put(session);
			logger.info("#new sessionId:{}#connect.", sessionID);
		}
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		Message request = (Message) e.getMessage();
		Message response = processRequest(request);
		e.getChannel().write(response);
	}

	/**
	 * 处理请求
	 * 
	 * @param request
	 *            请求消息
	 * @return
	 */
	private Message processRequest(Message request) {
		int cmdId = getCommandId(request);
		Message response = new Message(getResponseHeader(request, cmdId));
		Iterator<Interceptor> iterator = interceptors.iterator();

		new Invocation(cmdId, request, response, iterator).invoke();
		return response;
	}

	/**
	 * 获取请求头
	 * 
	 * @param request
	 *            请求
	 * @param cmdId
	 *            协议号
	 * @return
	 */
	private Header getResponseHeader(Message request, int cmdId) {
		Header header = request.getHeader().clone();
		header.setCommandId(cmdId + 1);
		return header;
	}

	/**
	 * 获取协议号
	 * 
	 * @param message
	 *            消息
	 * @return
	 */
	private int getCommandId(Message message) {
		return message.getHeader().getCommandId();
	}

	/**
	 * 设置错误消息体
	 * 
	 * @param state
	 *            响应状态
	 * @param errcode
	 *            错误号
	 * @param response
	 *            返回的消息
	 */
	private void setErrorMsg(int state, ErrorCode errcode, Message response) {

	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		logger.info("#sessionId:{}#connection closed#{}", ctx.getAttachment(), e.getChannel());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
	}
}
