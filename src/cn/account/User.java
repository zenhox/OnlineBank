package cn.account;

import java.io.Serializable;
import java.util.ArrayList;

import cn.bank.BankInfo;

/**
 * 这个是主要的Actor--用户
 * 用户是各种存储，投资的载体，银行主要的账户
 * @author Hox
 *
 */
public class User implements Serializable, Cloneable{
	private String username;//账户密码
	private int password;
	/*摆脱了基本类型偏执*/
	private ArrayList<SavingBal> savingBals; //死期存款的账户有多个，不能整合到一块
	private ChkingBal chkingBal; //活期存款的账户只有一个。
	private double loanBal;   //贷款金额
	
//	public String a;
//	public double chkTempMoney;
//	public double savTempMoney;
//	public double savTempYear;
	
	public User(String name,String psswd) {
		// TODO Auto-generated constructor stub
		username = name;
		password = psswd.hashCode();
		savingBals = new ArrayList<>(4);
		savingBals.add(new SavingBal(0, 0.5));
		savingBals.add(new SavingBal(0, 1));
		savingBals.add(new SavingBal(0,5));
		chkingBal = new ChkingBal(0);
	}

	public void setPassword(int password) {
		this.password = password;
	}

	public void addChkBal(double money) {
		chkingBal.deposit(money);
	}
	public void drawChkBal(double money) {
		chkingBal.draw(money);
	}
	public void addSavBal(double money,double time) {
		if(time==0.5)
		{
			savingBals.get(0).deposit(money);
		}
		else if(time==1) {
			savingBals.get(1).deposit(money);
		}
		else if(time==5) {
			savingBals.get(2).deposit(money);
		}
	}
	public boolean drawSavBal(double money,double time) {
		if(time==0.5)
		{
			return savingBals.get(0).draw(money);
		}
		else if(time==1) {
			return savingBals.get(1).draw(money);
		}
		else if(time==5) {
			return savingBals.get(2).draw(money);
		}else {
			return false;
		}
	}
	public String getUsername() {
		return username;
	}

	public int getPassword() {
		return password;
	}
	public void addLoan(double money) {
		loanBal += money;
	}
	public void setLoan(double money) {
		loanBal = money;
	}
	public double getLoanBal() {
		return loanBal;
	}

	public ArrayList<SavingBal> getSavingBals() {
		return savingBals;
	}

	public ChkingBal getChkingBal() {
		return chkingBal;
	}
}
