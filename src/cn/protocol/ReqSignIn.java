package cn.protocol;

import cn.account.User;

public class ReqSignIn extends ReqMessage{
	public ReqSignIn(User signInUser) {
		// TODO Auto-generated constructor stub
		super.payload = signInUser;
	}
}
