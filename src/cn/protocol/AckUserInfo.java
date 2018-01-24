package cn.protocol;

import cn.account.User;

public class AckUserInfo extends AckMessage{
	public AckUserInfo(User user) {
		// TODO Auto-generated constructor stub
		super.payload = user;
	}
}
