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
 * 银行服务器		
 */
public class BankServer {
	protected Bank bank;
	protected BankDB bankDB;  //改成Protected 是为了方便其他线程访问
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
	 * 					           内部类：服务器子线程                                           	   *
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
		/**不断的接受用户的各种请求
		 * 请求应该是一个    协议Package 种类的对象
		 * 根据接受到的对象是那种请求的实例来采取相应的处理
		 * 将各种交易类型 统一写成一个对象， 为了简单，这里采取的是客户端修改当前登陆的账户，然后把最新的账户发送过来，在服务器更新
		 */
		public synchronized void run() {
			while(true) {
				try {
					ReqMessage reqIn = (ReqMessage) inFromClient.readObject();
					if (reqIn instanceof ReqSignIn) {//请求登陆类型
						User signInUser = (User) reqIn.getPayload();
						synchronized (bank) {
							//登陆的只知道账户 和 密码
							if (bank.checkUsers(signInUser) == 1) {
								currentUser = bank.getUserInfo(signInUser.getUsername());  
								//登陆成功，把他的账户信息返还给他
								outToClient.writeObject(new AckUserInfo(currentUser));
							}else {
								outToClient.writeObject(new AckStatus(false));
							}
						}
						outToClient.reset();
					}
					
					else if (reqIn instanceof ReqSignUp) {//请求注册类型
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
					
					else if (reqIn instanceof ReqTransaction) {//请求各种交易类型
						User transactionUser = (User) reqIn.getPayload();
						synchronized(bank) {
							bank.updateUser(transactionUser);
							outToClient.writeObject(new AckStatus(true));
						}
						outToClient.reset();
					}
					else if (reqIn instanceof ReqUsrInfo) {//请求各种交易类型
						User theUsr = (User) reqIn.getPayload();
						synchronized(bank) {
							outToClient.writeObject(new AckUserInfo(bank.getUserInfo(theUsr.getUsername())));
						}
						outToClient.reset();
					}
				} catch (Exception e) {try {
					bankDB.updateDB(bank.getUsers()); //下线之前保留信息
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}return;} 
			}
		}
	}
}
