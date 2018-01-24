package cn.account;

/**
 * 余额接口，将提供两种实现. 一个是定期存款 一个是活期存款
 * 不同存款的利息计算方式 取款方式 将以不同的方法实现
 * 因为难点就是 对 死期和活期的操作， 对不同利息的操作 是不一样的
 * 如果把他们写成类， 自己对应不同的操作 
 * 可以省去很多判断。 而且可以利用多态，对这些balance 妥善处理
 * @author Hox
 *
 */
public interface Balance {
	public boolean draw(double money);
	public void deposit(double money);
	public double check();
	public void insterestCal();  //计算利息
}
