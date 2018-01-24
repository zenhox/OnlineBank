package cn.protocol;


/**
 * 这是响应类型报文的一个具体实现 ： 回应状态。 往往payload会是一个User
 * @author Hox
 *
 */
public class AckStatus extends AckMessage{
	public AckStatus(boolean status) {
		// TODO Auto-generated constructor stub
		super.payload = status;
	}
}
