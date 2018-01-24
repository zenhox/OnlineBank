package cn.account;

import java.io.Serializable;

import cn.bank.BankInfo;

/**
 * 这个是活期存款的余额
 * @author Hox
 *
 */
public class ChkingBal implements Balance,Serializable,Cloneable{
	private double balance;


	public ChkingBal(double theBal) {
		// TODO Auto-generated constructor stub
		balance = theBal;

	}
	
	/**
	 * 取款
	 */
	public boolean draw(double minusMoney) {
		if (balance >= minusMoney) {
			balance -= minusMoney;
			return true;
		}
		return false;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * 存款
	 */
	public void deposit(double addMoney) {
		balance += addMoney;
	}
	
	/**
	 * 计算利息
	 */
	public void insterestCal() {
		balance += balance * BankInfo.cheingIns; //调用的是银行的信息
	}
	/**
	 * 查询余额
	 */
	public double check() {
		return balance;
	}
}
