package cn.server;

import java.sql.SQLException;

/**
 * 这个类用于定期更新数据库
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
	//每十秒钟更新一下数据库
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
