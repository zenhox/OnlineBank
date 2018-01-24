package cn.account;

import java.io.Serializable;
import java.util.IllegalFormatCodePointException;

import cn.bank.Bank;
import cn.bank.BankInfo;

/**
 * 单独把saving balance 取出作为一个类 
 * 是为了更方便的处理各种存款  
 * 因为 saving balance 可以有很多种 很多份  : 1 years 2 years 5 years
 * 如果把他写成类存储到账户， 就可以很容易的 通过 balance 和 time 属性区分他们， 以及他们自身的 各种操作来管理账户
 * @author Hox
 *
 */
public class SavingBal implements Balance,Serializable,Cloneable{
	private double balance;
	private double insterest; 
	private double time;   //这是区分不同saving balance 的主要属性
	private double rate;
	private long initTime;  //开始存的时候的时间
	
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
		initTime = System.currentTimeMillis();  //开始计算时间
	
	}
	@Override
	public boolean draw(double money) {
		// TODO Auto-generated method stub
		if(balance < money)
		return false;
		else {
			if ((System.currentTimeMillis()-initTime)/1000 < time*15) { //如果时间还没到
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
		if ((nowTime-initTime)/1000 >= time*15) { //如果时间到了
			if(time==0.5)
				return;
			balance += insterest;
			insterest = 0;
			initTime = nowTime;
		}
	}
}
