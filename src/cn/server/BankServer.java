package cn.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import cn.account.User;
import cn.bank.Bank;
import cn.bank.BankInfo;
import cn.protocol.AckMessage;
import cn.protocol.AckStatus;
import cn.protocol.AckUserInfo;
import cn.protocol.ReqMessage;
import cn.protocol.ReqSignIn;
import cn.protocol.ReqSignUp;
import cn.protocol.ReqTransaction;
import cn.protocol.ReqUsrInfo;
/*
 * ���з�����		
 */
public class BankServer {
	protected Bank bank;
	protected BankDB bankDB;  //�ĳ�Protected ��Ϊ�˷��������̷߳���
	protected ServerSocket serverSocket;
	
	public BankServer() throws IOException, ClassNotFoundException, SQLException {
		// TODO Auto-generated constructor stub
		serverSocket = new ServerSocket(BankInfo.BANK_PORT);
		bankDB = new BankDB();
		bank = new Bank();
		bank.setUsers(bankDB.getUsers());
		
		Runnable insterestCal = new InsterestCal(this);
		Runnable updateBankDB = new UpdateDB(this);
		Thread workerOne = new Thread(updateBankDB);
		Thread workerTwo = new Thread(insterestCal);
		workerOne.start();
		workerTwo.start();
	}
	
	/***********************************************************
	 * 					           �ڲ��ࣺ���������߳�                                           	   *
	 ***********************************************************/
	public class ServerThread extends Thread{
		private ObjectOutputStream outToClient;
		private ObjectInputStream inFromClient;
		private User currentUser;
		
		public ServerThread(Socket socket) throws IOException {
			// TODO Auto-generated constructor stub
			outToClient = new ObjectOutputStream(socket.getOutputStream());
			inFromClient = new ObjectInputStream(socket.getInputStream());
		}
		/**���ϵĽ����û��ĸ�������
		 * ����Ӧ����һ��    Э��Package ����Ķ���
		 * ���ݽ��ܵ��Ķ��������������ʵ������ȡ��Ӧ�Ĵ���
		 * �����ֽ������� ͳһд��һ������ Ϊ�˼򵥣������ȡ���ǿͻ����޸ĵ�ǰ��½���˻���Ȼ������µ��˻����͹������ڷ���������
		 */
		public synchronized void run() {
			while(true) {
				try {
					ReqMessage reqIn = (ReqMessage) inFromClient.readObject();
					if (reqIn instanceof ReqSignIn) {//�����½����
						User signInUser = (User) reqIn.getPayload();
						synchronized (bank) {
							//��½��ֻ֪���˻� �� ����
							if (bank.checkUsers(signInUser) == 1) {
								currentUser = bank.getUserInfo(signInUser.getUsername());  
								//��½�ɹ����������˻���Ϣ��������
								outToClient.writeObject(new AckUserInfo(currentUser));
							}else {
								outToClient.writeObject(new AckStatus(false));
							}
						}
						outToClient.reset();
					}
					
					else if (reqIn instanceof ReqSignUp) {//����ע������
						User signUpUser = (User) reqIn.getPayload();
						synchronized (bank) {
							if (bank.addUser(signUpUser)) {
								outToClient.writeObject(new AckStatus(true));
							}else {
								outToClient.writeObject(new AckStatus(false));
							}
						}
						outToClient.reset();
					}
					
					else if (reqIn instanceof ReqTransaction) {//������ֽ�������
						User transactionUser = (User) reqIn.getPayload();
						synchronized(bank) {
							bank.updateUser(transactionUser);
							outToClient.writeObject(new AckStatus(true));
						}
						outToClient.reset();
					}
					else if (reqIn instanceof ReqUsrInfo) {//������ֽ�������
						User theUsr = (User) reqIn.getPayload();
						synchronized(bank) {
							outToClient.writeObject(new AckUserInfo(bank.getUserInfo(theUsr.getUsername())));
						}
						outToClient.reset();
					}
				} catch (Exception e) {try {
					bankDB.updateDB(bank.getUsers()); //����֮ǰ������Ϣ
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}return;} 
			}
		}
	}
}
