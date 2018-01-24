package cn.account;

import java.io.Serializable;
import java.util.IllegalFormatCodePointException;

import cn.bank.Bank;
import cn.bank.BankInfo;

/**
 * ������saving balance ȡ����Ϊһ���� 
 * ��Ϊ�˸�����Ĵ�����ִ��  
 * ��Ϊ saving balance �����кܶ��� �ܶ��  : 1 years 2 years 5 years
 * �������д����洢���˻��� �Ϳ��Ժ����׵� ͨ�� balance �� time �����������ǣ� �Լ���������� ���ֲ����������˻�
 * @author Hox
 *
 */
public class SavingBal implements Balance,Serializable,Cloneable{
	private double balance;
	private double insterest; 
	private double time;   //�������ֲ�ͬsaving balance ����Ҫ����
	private double rate;
	private long initTime;  //��ʼ���ʱ���ʱ��
	
	public SavingBal(double balance,double time) {
		// TODO Auto-generated constructor stub
		this.balance = balance;
		this.time = time;
		if(time==0.5)
			rate = BankInfo.savingIns_Half;
		else if(time==1)
			rate = BankInfo.savingIns_One;
		else if(time==5)
			rate = BankInfo.savingIns_Five;
		initTime = System.currentTimeMillis();  //��ʼ����ʱ��
	
	}
	@Override
	public boolean draw(double money) {
		// TODO Auto-generated method stub
		if(balance < money)
		return false;
		else {
			if ((System.currentTimeMillis()-initTime)/1000 < time*15) { //���ʱ�仹û��
				balance -= money;
				insterest = 0;
				initTime = System.currentTimeMillis();
				return true;
			}else {
				balance-=money;
				return true;
			}
		}
	}
	@Override
	public void deposit(double money) {
		// TODO Auto-generated method stub
		balance+=money;
	}
	public double getBal() {
		return balance;
	}
	public double getIns() {
		return insterest;
	}
	public double check() {
		// TODO Auto-generated method stub
		return balance + insterest;
	}
	@Override
	public void insterestCal() {
		// TODO Auto-generated method stub
		insterest += balance * rate;
		long nowTime = System.currentTimeMillis();
		if ((nowTime-initTime)/1000 >= time*15) { //���ʱ�䵽��
			if(time==0.5)
				return;
			balance += insterest;
			insterest = 0;
			initTime = nowTime;
		}
	}
}
