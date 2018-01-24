package cn.protocol;

import java.io.Serializable;

/**
 * ����һ�������� �� ��Ӧ���ͱ��ĵĳ���
 * �����ֵ�payload ��һ�����壬�����Ų�ͬ��Ӧ�Ķ���
 * @author Hox
 *
 */
public abstract class AckMessage implements Serializable{
	protected Object payload;

	public Object getPayload() {
		return payload;
	}
}
