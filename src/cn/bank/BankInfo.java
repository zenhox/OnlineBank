package cn.bank;


/**
 * ��������й�������Ϣ�� 
 * ���������Լ��������ݿ��IP���˿ڵȵ���Ϣ��
 * ����һ��������
 * @author Hox
 *
 */
public class BankInfo {
	/*��Ϣ��Ϣ*/
	public static double cheingIns = 0.015;
	public static double savingIns_Half = 0.02;
	public static double savingIns_One = 0.03;
	public static double savingIns_Five = 0.045;
	public static double loanIns = 0.05;
	
	/*������Ϣ*/
	public static final int BANK_PORT = 8080;  //���ж˿�
	public static final String BANK_DB_PORT = "3306";  //���ݿ�˿�
	public static final String ADDRESS = "127.0.0.1";  //������IP��ַ
}
