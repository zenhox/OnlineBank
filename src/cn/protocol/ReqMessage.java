package cn.protocol;

import java.io.Serializable;

public abstract class ReqMessage implements Serializable{
	protected Object payload;

	public Object getPayload() {
		return payload;
	}
}
