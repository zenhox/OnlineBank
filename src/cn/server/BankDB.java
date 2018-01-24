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
 * ���ݿ��࣬�ṩ�������ݿ��������
 * �����ݿ��ж�ȡ��Ϣ
 * @author Hox
 *
 */
public class BankDB {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	/******************************************************************/
	//					�Ӵ˴��������ݿ�����
	private static final String USER = "root";
	private static final String PASSWD = "";
	private static final String DB_NAME = "bank";
	/******************************************************************/
	//  				�����������Ϣ��дURL
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
	 * ���ض��Ķ���д�����ݿ�
	 * @param object
	 * @throws SQLException
	 */
	public synchronized void updateDB(Object object) throws SQLException {
		updateStatement.setObject(1,object);
		updateStatement.executeUpdate();
		updateStatement.clearParameters();
	}
	
	/**
	 * �����ݿ��л�ȡ�û�����
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
			} catch (Exception e) {//˵����ʱ���ݿ⻹�ǿյ� ����һ���յ��û���
				// TODO: handle exception
				return usersInDB;
			}
		}
		return usersInDB;  //����Ҳ�����˵�����л����ĵã�ֱ�Ӱ��µ���������� �����ﴦ��nullPointer���� 
	}
}
