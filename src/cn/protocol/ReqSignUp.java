package cn.protocol;

import cn.account.User;

public class ReqSignUp extends ReqMessage{
	public ReqSignUp(User signUpUser) {
		// TODO Auto-generated constructor stub
		super.payload = signUpUser;
	}
}
