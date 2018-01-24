package cn.protocol;

import cn.account.User;

public class ReqTransaction extends ReqMessage{
	public ReqTransaction(Object message) {
		// TODO Auto-generated constructor stub
		super.payload = message;
	}
}
