package cn.protocol;

import java.io.Serializable;

/**
 * 这是一个抽象类 ： 响应类型报文的超类
 * 报文种的payload 是一个载体，承载着不同相应的对象
 * @author Hox
 *
 */
public abstract class AckMessage implements Serializable{
	protected Object payload;

	public Object getPayload() {
		return payload;
	}
}
