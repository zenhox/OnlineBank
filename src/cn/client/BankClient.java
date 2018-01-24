package cn.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import cn.account.User;
import cn.bank.BankInfo;
import cn.protocol.AckMessage;
import cn.protocol.AckUserInfo;
import cn.protocol.ReqMessage;
import cn.protocol.ReqSignIn;
import cn.protocol.ReqSignUp;
import cn.protocol.ReqTransaction;
import cn.protocol.ReqUsrInfo;

/**
 * 这是客户端，提供了一些 基本功能，提供给客户端界面使用
 * @author Hox
 *
 */
public class BankClient {
	private User user;
	private Socket socket;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	private ObjectOutputStream outToServer;
	private ObjectInputStream inFromServer;
	
	public BankClient() throws UnknownHostException, IOException {
		socket = new Socket(BankInfo.ADDRESS, BankInfo.BANK_PORT);  //调用信息类的内容
		outToServer = new ObjectOutputStream(socket.getOutputStream());
		inFromServer = new ObjectInputStream(socket.getInputStream());
	}
	/**
	 * 这个函数向服务器发送注册请求，如果注册成功，就登陆,并且返回true。
	 * @param user
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public boolean reqSignUp(User signUpUser) throws IOException, ClassNotFoundException {
		ReqMessage reqMessage = new ReqSignUp(signUpUser);  //申请注册的报文
		outToServer.writeObject(reqMessage);
		outToServer.flush();
		outToServer.reset();
		while(true) {
			AckMessage ackMessage = (AckMessage) inFromServer.readObject();
			if (ackMessage != null) {
				if (ackMessage.getPayload().equals(true)) {
					this.user = signUpUser;
					return true;
				}
				if (ackMessage.getPayload().equals(false)) {
					return false;
				}
			}
		}	
	}
	
	/**
	 * 这个方法向服务器提交 登陆请求， 如果登陆成功， 服务器会把 他的账户信息返还给他
	 * @param signInUser
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public boolean reqSignIn(User signInUser) throws IOException, ClassNotFoundException {
		ReqMessage reqMessage = new ReqSignIn(signInUser);
		outToServer.writeObject(reqMessage);
		outToServer.flush();
		outToServer.reset();
		while(true) {
			AckMessage ackMessage = (AckMessage) inFromServer.readObject();
			if (ackMessage != null) {
				if (ackMessage.getPayload() instanceof User) {
					this.user = (User) ackMessage.getPayload();
					return true;
				}
				if (ackMessage.getPayload().equals(false)) {
					return false;
				}
			}
		}	
	}
	
	public boolean reqTransaction(User theUser) throws IOException, ClassNotFoundException {
		User user = theUser;
		ReqMessage reqMessage = new ReqTransaction(user);
		outToServer.writeObject(reqMessage);
		outToServer.flush();
		outToServer.reset();
		while(true) {
			AckMessage ackMessage = (AckMessage) inFromServer.readObject();
			if (ackMessage != null) {
				if (ackMessage.getPayload().equals(true)) {
					return true;
				}
				else {
					return false;
				}
			}
		}
	}
	
	public void reqUsrInfo() throws IOException, ClassNotFoundException {
		ReqMessage reqMessage = new ReqUsrInfo(user);
		outToServer.writeObject(reqMessage);
		outToServer.flush();
		outToServer.reset();
		while(true) {
			AckMessage ackMessage = (AckMessage) inFromServer.readObject();
			if (ackMessage instanceof AckUserInfo) {
				this.user = (User) ackMessage.getPayload();
				return;
			}
		}
	}
}
