package cn.server;

import java.sql.SQLException;

/**
 * ��������ڶ��ڸ������ݿ�
 * @author Hox
 *
 */
public class UpdateDB implements Runnable{
	private BankServer bankServer;
	
	public UpdateDB(BankServer bankServer) {
		// TODO Auto-generated constructor stub
		this.bankServer = bankServer;
	}
	@Override
	//ÿʮ���Ӹ���һ�����ݿ�
	public synchronized void run() {
		// TODO Auto-generated method stub
		while(true) {
			try {
				bankServer.bankDB.updateDB(bankServer.bank.getUsers());
				Thread.sleep(3000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
