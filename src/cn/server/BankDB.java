package cn.server;

import java.util.ArrayList;
import java.util.HashMap;


import cn.account.User;
import cn.bank.BankInfo;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库类，提供更新数据库的能力。
 * 从数据库中读取信息
 * @author Hox
 *
 */
public class BankDB {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	/******************************************************************/
	//					从此处调整数据库连接
	private static final String USER = "root";
	private static final String PASSWD = "";
	private static final String DB_NAME = "bank";
	/******************************************************************/
	//  				根据上面的信息填写URL
	private static final String URL = "jdbc:mysql://localhost:" 
										+ BankInfo.BANK_DB_PORT + "/"+
										DB_NAME + 
										"?user=" + USER + 
										"&password=" + PASSWD;
	
	private PreparedStatement updateStatement;	
	private PreparedStatement getInfoStatement;
	
	private ArrayList<User> usersInDB;
	
	public BankDB() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated constructor stub
		Class.forName(DRIVER);
		Connection connection = DriverManager.getConnection(URL);
		updateStatement = connection.prepareStatement("UPDATE bankUsers SET users=? WHERE flag=1");
		getInfoStatement = connection.prepareStatement("SELECT users FROM bankUsers  WHERE flag=1");
		usersInDB = new ArrayList<>();
	}
	
	/**
	 * 将特定的对象写入数据库
	 * @param object
	 * @throws SQLException
	 */
	public synchronized void updateDB(Object object) throws SQLException {
		updateStatement.setObject(1,object);
		updateStatement.executeUpdate();
		updateStatement.clearParameters();
	}
	
	/**
	 * 从数据库中获取用户数组
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<User> getUsers() throws SQLException, IOException, ClassNotFoundException {
		ResultSet resultSet = getInfoStatement.executeQuery();
		while(resultSet.next()) {
			ObjectInputStream ois = null;
			try {
				byte[] data = resultSet.getBytes(1);
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
				ois = new ObjectInputStream(byteArrayInputStream);
				usersInDB = (ArrayList<User>) ois.readObject();
			} catch (Exception e) {//说明此时数据库还是空的 返回一个空的用户组
				// TODO: handle exception
				return usersInDB;
			}
		}
		return usersInDB;  //如果找不到，说明银行还是心得，直接把新的数组给他。 在这里处理nullPointer错误 
	}
}
