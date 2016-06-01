package com.gameserver.net;

public enum MsgType {

	JSON(0), BYTE(1);

	private byte value;

	private MsgType(int value) {
		this.value = (byte) value;
	}

	public byte getValue() {
		return value;
	}
}
