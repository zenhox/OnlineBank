package cn.account;

/**
 * ���ӿڣ����ṩ����ʵ��. һ���Ƕ��ڴ�� һ���ǻ��ڴ��
 * ��ͬ������Ϣ���㷽ʽ ȡ�ʽ ���Բ�ͬ�ķ���ʵ��
 * ��Ϊ�ѵ���� �� ���ںͻ��ڵĲ����� �Բ�ͬ��Ϣ�Ĳ��� �ǲ�һ����
 * ���������д���࣬ �Լ���Ӧ��ͬ�Ĳ��� 
 * ����ʡȥ�ܶ��жϡ� ���ҿ������ö�̬������Щbalance ���ƴ���
 * @author Hox
 *
 */
public interface Balance {
	public boolean draw(double money);
	public void deposit(double money);
	public double check();
	public void insterestCal();  //������Ϣ
}
