package cn.protocol;


/**
 * ������Ӧ���ͱ��ĵ�һ������ʵ�� �� ��Ӧ״̬�� ����payload����һ��User
 * @author Hox
 *
 */
public class AckStatus extends AckMessage{
	public AckStatus(boolean status) {
		// TODO Auto-generated constructor stub
		super.payload = status;
	}
}
