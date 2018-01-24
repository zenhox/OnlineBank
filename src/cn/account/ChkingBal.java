package cn.account;

import java.io.Serializable;

import cn.bank.BankInfo;

/**
 * ����ǻ��ڴ������
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
	 * ȡ��
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
	 * ���
	 */
	public void deposit(double addMoney) {
		balance += addMoney;
	}
	
	/**
	 * ������Ϣ
	 */
	public void insterestCal() {
		balance += balance * BankInfo.cheingIns; //���õ������е���Ϣ
	}
	/**
	 * ��ѯ���
	 */
	public double check() {
		return balance;
	}
}
