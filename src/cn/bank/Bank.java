package cn.bank;

import java.io.Serializable;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.account.SavingBal;
import cn.account.User;

/**
 * �����࣬���ڴ洢�����û��˻���Ϣ
 * �ṩһЩ�ӿڣ�������Ϣ  ������� ע���˻��ȵ�
 * @author Hox
 *
 */
public class Bank implements Serializable{
	private ArrayList<User> users;
	//ÿ���û�������һ���ض���ID ��Ϊ���ǵ�Keyֵ�� ����൱�����ǵ�QQ���룬�������п��š��������û�����
	//ѡ��ID �ĺô��ǣ������������ĸ��û����ʷ�������
	
	public Bank() {
		// TODO Auto-generated constructor stub
		users = new ArrayList<>();
	}
	
	/**
	 * ֮���Է���int����Ϊ���������
	 * 1  �˺Ŵ�����������ȷ
	 * 2 �˺Ŵ��ڵ����������
	 * 3 �˺Ų�����
	 * @param username
	 * @param password
	 * @return
	 */
	public synchronized int checkUsers(User theUser) {
		//����HASH �� ���ҷ��� 1 2 3
		for(User u:users) {
			if (u.getUsername().equals(theUser.getUsername())) {
				if (u.getPassword() == theUser.getPassword()) {
					return 1;
				}
				return 2;
			}
		}
		return 3;
	}
	/**
	 * ����һ���˻� ע�� ����д�������ڲ��� �ᱻ���ܵ�
	 * @param username
	 * @param password
	 */
	public synchronized boolean addUser(User user) {
		if (checkUsers(user) == 3) {//����˺�û�б�����ע��
			users.add(user);
			//ע�⣬����ʹ����ID ��Ϊ ���µ�id��  ���Կ��Կ��� �û���ID �Ǵ�1 ��ʼ�ġ�
			return true;
		}
		return false;
	}
	
	/**
	 * ��������ʱ���ô˽ӿ����������˼�����Ϣ
	 */
	public synchronized void inserestCal() {
		for(User u:users) {
			//�������͵��˻��ֱ������Ϣ
			u.getChkingBal().insterestCal();
			for(SavingBal s:u.getSavingBals()) {
				s.insterestCal();
			}
			u.addLoan(u.getLoanBal()*BankInfo.loanIns);
		}
	}
	
	public synchronized User getUserInfo(String name) {
		for(User u:users) {
			if (u.getUsername().equals(name)) {
				return u;
			}
		}
		return null;
	}
	
	public synchronized void updateUser(User theUser) {
		users.remove(getUserInfo(theUser.getUsername()));
		users.add(theUser);
	}
	public synchronized ArrayList<User> getUsers() {
		return users;
	}
	public synchronized void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	
}
