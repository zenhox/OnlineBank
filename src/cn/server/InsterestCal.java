package cn.server;

/**
 * ���ǵ�����һ���߳� ���԰���д��һ����
 * @author Hox
 *
 */
public class InsterestCal implements Runnable{
	private BankServer bankServer;
	public InsterestCal(BankServer theBank) {
		// TODO Auto-generated constructor stub
		bankServer = theBank;
	}
	public synchronized void run() {
		while(true) {
			bankServer.bank.inserestCal();
			try {
				Thread.sleep(10000);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}

