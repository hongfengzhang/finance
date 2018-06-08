package com.waben.stock.futuresgateway.yingtou.rabbitmq.message;

public class TickSizeMessage {

	private int tickerId;

	private int field;

	private int size;

	public TickSizeMessage() {
		super();
	}

	public TickSizeMessage(int tickerId, int field, int size) {
		super();
		this.tickerId = tickerId;
		this.field = field;
		this.size = size;
	}

	public int getTickerId() {
		return tickerId;
	}

	public void setTickerId(int tickerId) {
		this.tickerId = tickerId;
	}

	public int getField() {
		return field;
	}

	public void setField(int field) {
		this.field = field;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
