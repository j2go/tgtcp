package com.gameserver.net.codec;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.CorruptedFrameException;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import com.gameserver.net.Header;
import com.gameserver.net.Message;

/**
 * 对包的头文件进行解码 header协议格式: { tag byte 协议头标志位 encode byte encrypt byte State byte
 * extend byte sessionid long length int commandId int }
 * 
 * @author shitg
 *
 */
public class HeaderDecoder extends FrameDecoder {

	/** 头文件长度 **/
	public static final int HEAD_LENGHT = 30;
	/** 包头标志 **/
	public static final byte PACKAGE_TAG = 0x01;

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		if (buffer.readableBytes() < HEAD_LENGHT) {
			return null;
		}
		buffer.markReaderIndex();
		byte tag = buffer.readByte();
		if (tag != PACKAGE_TAG) {
			throw new CorruptedFrameException("非法协议包");
		}
		byte encode = buffer.readByte();
		byte encrypt = buffer.readByte();
		byte state = buffer.readByte();
		byte type = buffer.readByte();
		byte extend = buffer.readByte();
		long sessionid = buffer.readLong();
		int length = buffer.readInt();
		int commandId = buffer.readInt();

		if (buffer.readableBytes() < length) {
			buffer.resetReaderIndex();
			return null;
		}

		Header header = new Header(encode, encrypt, state, type, extend, sessionid, length, commandId);
		Message message = new Message(header, buffer.readBytes(length));

		return message;
	}

}
