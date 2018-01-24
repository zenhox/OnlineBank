package cn.account;

import java.io.Serializable;
import java.util.ArrayList;

import cn.bank.BankInfo;

/**
 * �������Ҫ��Actor--�û�
 * �û��Ǹ��ִ洢��Ͷ�ʵ����壬������Ҫ���˻�
 * @author Hox
 *
 */
public class User implements Serializable, Cloneable{
	private String username;//�˻�����
	private int password;
	/*�����˻�������ƫִ*/
	private ArrayList<SavingBal> savingBals; //���ڴ����˻��ж�����������ϵ�һ��
	private ChkingBal chkingBal; //���ڴ����˻�ֻ��һ����
	private double loanBal;   //������
	
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
