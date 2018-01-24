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
 * ���ǿͻ��ˣ��ṩ��һЩ �������ܣ��ṩ���ͻ��˽���ʹ��
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
		socket = new Socket(BankInfo.ADDRESS, BankInfo.BANK_PORT);  //������Ϣ�������
		outToServer = new ObjectOutputStream(socket.getOutputStream());
		inFromServer = new ObjectInputStream(socket.getInputStream());
	}
	/**
	 * ������������������ע���������ע��ɹ����͵�½,���ҷ���true��
	 * @param user
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public boolean reqSignUp(User signUpUser) throws IOException, ClassNotFoundException {
		ReqMessage reqMessage = new ReqSignUp(signUpUser);  //����ע��ı���
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
	 * ���������������ύ ��½���� �����½�ɹ��� ��������� �����˻���Ϣ��������
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
