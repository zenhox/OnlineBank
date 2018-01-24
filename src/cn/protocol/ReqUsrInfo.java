package cn.protocol;

import cn.account.User;

public class ReqUsrInfo extends ReqMessage {
	public ReqUsrInfo(User theUser) {
		// TODO Auto-generated constructor stub
		super.payload = theUser;
	}
}
