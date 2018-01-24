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
 * 银行类，用于存储所有用户账户信息
 * 提供一些接口：计算利息  检查密码 注册账户等等
 * @author Hox
 *
 */
public class Bank implements Serializable{
	private ArrayList<User> users;
	//每个用户都会有一个特定的ID 作为他们的Key值。 这个相当于他们的QQ号码，或者银行卡号。区别于用户名。
	//选用ID 的好处是，便于区分是哪个用户访问服务器。
	
	public Bank() {
		// TODO Auto-generated constructor stub
		users = new ArrayList<>();
	}
	
	/**
	 * 之所以返回int是因为有三种情况
	 * 1  账号存在且密码正确
	 * 2 账号存在但是密码错误
	 * 3 账号不存在
	 * @param username
	 * @param password
	 * @return
	 */
	public synchronized int checkUsers(User theUser) {
		//遍历HASH 表 并且返回 1 2 3
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
	 * 增加一个账户 注意 密码写到对象内部是 会被加密的
	 * @param username
	 * @param password
	 */
	public synchronized boolean addUser(User user) {
		if (checkUsers(user) == 3) {//如果账号没有被别人注册
			users.add(user);
			//注意，这里使他的ID 成为 最新的id。  所以可以看到 用户的ID 是从1 开始的。
			return true;
		}
		return false;
	}
	
	/**
	 * 服务器分时调用此接口来给所有人计算利息
	 */
	public synchronized void inserestCal() {
		for(User u:users) {
			//两种类型的账户分别计算利息
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
