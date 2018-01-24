package cn.bank;


/**
 * 这个是银行公开的信息类 
 * 包括银行以及银行数据库的IP，端口等等信息。
 * 这是一个数据类
 * @author Hox
 *
 */
public class BankInfo {
	/*利息信息*/
	public static double cheingIns = 0.015;
	public static double savingIns_Half = 0.02;
	public static double savingIns_One = 0.03;
	public static double savingIns_Five = 0.045;
	public static double loanIns = 0.05;
	
	/*网络信息*/
	public static final int BANK_PORT = 8080;  //银行端口
	public static final String BANK_DB_PORT = "3306";  //数据库端口
	public static final String ADDRESS = "127.0.0.1";  //服务器IP地址
}
