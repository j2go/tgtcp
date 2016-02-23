package com.gameserver.net;

public enum State {
	FAIL(0), SUCC(1), PUSH(2), SPEC(3), EXCEPTION(4), REDIRECT(5);

	private byte value;

	private State(int value) {
		this.value = (byte) value;
	}

	public byte getValue() {
		return value;
	}
}
