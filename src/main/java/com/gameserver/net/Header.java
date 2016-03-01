package com.gameserver.net;

/**
 * 请求和返回的头文件
 * 
 * @author shitg
 * 
 */
public class Header implements Cloneable {

	/** 数据编码格式。已定义：0：UTF-8，1：GBK，2：GB2312，3：ISO8859-1 **/
	private byte encode;
	/** 加密类型。0表示不加密 **/
	private byte encrypt;
	/**
	 * 状态码 0 执行失败 1 正常返回 2 推送 3 特殊显示消息 4 程序异常
	 **/
	private byte state;
	/**
	 * 消息类型 0 json 1 protobuf
	 **/
	private byte type;
	/** 用于扩展协议。暂未定义任何值 **/
	private byte extend;
	/** 会话ID **/
	private long sessionid;
	/** 数据包长 **/
	private int length;
	/** 命令 **/
	private int commandId;

	@Override
	public Header clone() {
		try {
			return (Header) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Header() {
	}

	public Header(long sessionid) {
		this.encode = 0;
		this.encrypt = 0;
		this.sessionid = sessionid;
	}

	public Header(byte encode, byte encrypt, byte state, byte type, byte extend, long sessionid, int length,
			int commandId) {
		this.encode = encode;
		this.encrypt = encrypt;
		this.state = state;
		this.setType(type);
		this.extend = extend;
		this.sessionid = sessionid;
		this.length = length;
		this.commandId = commandId;
	}

	public byte getEncode() {
		return encode;
	}

	public void setEncode(byte encode) {
		this.encode = encode;
	}

	public byte getEncrypt() {
		return encrypt;
	}

	public void setEncrypt(byte encrypt) {
		this.encrypt = encrypt;
	}

	public byte getState() {
		return state;
	}

	public void setState(byte state) {
		this.state = state;
	}

	public byte getExtend() {
		return extend;
	}

	public void setExtend(byte extend) {
		this.extend = extend;
	}

	public Long getSessionid() {
		return sessionid;
	}

	public void setSessionid(Long sessionid) {
		this.sessionid = sessionid;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getCommandId() {
		return commandId;
	}

	public void setCommandId(int commandId) {
		this.commandId = commandId;
	}

	@Override
	public String toString() {
		return "header [encode=" + encode + ",encrypt=" + encrypt + ",state=" + state + ",extend2=" + extend
				+ ",sessionid=" + sessionid + ", =" + length + ",commandId=" + commandId + "]";
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

}
