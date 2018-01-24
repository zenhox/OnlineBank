package cn.client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.TableModel;

import cn.account.User;
import cn.bank.BankInfo;

/**
 * 这是客户端的界面类
 * 调用客户端的各种接口，来实现不同的UI事件处理功能
 * @author Hox
 *
 */
public class ClientMenu {
	private static BankClient bankClient;
	protected static LoginMenu loginMenu;
	protected static SignUpMenu signUpMenu;
	protected static MainMenu mainMenu;
	
	
	public static class LoginMenu extends JFrame implements ActionListener{
		//protected JFrame menu; 
		protected JLabel username;
		protected JLabel password;
		protected JTextField nameInput;
		protected JPasswordField pswdInput;
		protected JButton signIn;
		protected JButton signUp;
		
		public LoginMenu() {
			// TODO Auto-generated constructor stub
			setTitle("请登陆");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setLocationRelativeTo(null);
			setSize(330, 140);
			username = new JLabel("Username:");
			password = new JLabel("Password:");
			nameInput = new JTextField();
			//nameInput.setSize(100,5);
			pswdInput = new JPasswordField();
			
			//pswdInput.setSize(100,5);
			signIn = new JButton("Sign In");
			signUp = new JButton("Sign Up");
			
			signIn.addActionListener(this);
			signIn.setMnemonic(java.awt.event.KeyEvent.VK_ENTER);
			signUp.addActionListener(this);
			
			GridLayout gridLayout = new GridLayout(3, 2);
			setLayout(gridLayout);
			add(username);
			add(nameInput);
			add(password);
			add(pswdInput);
			add(signIn);
			add(signUp);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton source = (JButton) e.getSource();
			if (source.equals(signIn)) {
				String nameSignIn = nameInput.getText();
				String pswdSignIn = pswdInput.getText();
				User userSignIn = new User(nameSignIn, pswdSignIn);
				try {
					if (bankClient.reqSignIn(userSignIn)) {

						loginMenu.setVisible(false);
						mainMenu = new MainMenu();
						mainMenu.frame.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "用户名或密码错误", "消息", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
			if (source.equals(signUp)) {
				signUpMenu = new SignUpMenu();
				signUpMenu.setVisible(true);
			}
		}
	}
	public static class SignUpMenu extends JFrame implements ActionListener{
		protected JLabel username;
		protected JLabel passwordOne;
		protected JLabel passwordTwo;
		protected JTextField nameInput;
		protected JPasswordField pswdInputOne;
		protected JPasswordField pswdInputTwo;
		protected JButton signUp;
		protected JButton cancle;
		public SignUpMenu() {
			// TODO Auto-generated constructor stub
			setTitle("注册界面");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setLocationRelativeTo(null);
			setSize(330, 240);
			username = new JLabel("Username:");
			passwordOne = new JLabel("Password:");
			passwordTwo = new JLabel("Repeat password:");
			nameInput = new JTextField();
			//nameInput.setSize(100,5);
			pswdInputOne = new JPasswordField();
			pswdInputTwo = new JPasswordField();
			//pswdInput.setSize(100,5);
			signUp = new JButton("Sign Up");
			cancle = new JButton("Cancle");
			
			cancle.addActionListener(this);
			signUp.addActionListener(this);
			
			GridLayout gridLayout = new GridLayout(4, 2);
			setLayout(gridLayout);
			add(username);
			add(nameInput);
			add(passwordOne);
			add(pswdInputOne);
			add(passwordTwo);
			add(pswdInputTwo);
			add(signUp);
			add(cancle);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton source = (JButton) e.getSource();
			if (source.equals(signUp)) {
				if (pswdInputOne.getText().equals(pswdInputTwo.getText())) {
					String nameSignIn = nameInput.getText();
					String pswdSignIn = pswdInputOne.getText();
					User userSignUp = new User(nameSignIn, pswdSignIn);
					try {
						if (bankClient.reqSignUp(userSignUp)) {
							JOptionPane.showMessageDialog(null, "注册成功", "消息", JOptionPane.INFORMATION_MESSAGE);
							setVisible(false);
						}
						else {
							JOptionPane.showMessageDialog(null, "用户名已经存在", "消息", JOptionPane.ERROR_MESSAGE);

						}
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "两次密码不一样", "消息", JOptionPane.ERROR_MESSAGE);
				}
			}
			if (source.equals(cancle)) {
				setVisible(false);
			}
		}
	}
	
	public static class MainMenu {

		public JFrame frame;
		private JTable table;

		/**
		 * Create the application.
		 */
		public MainMenu() {
			initialize();
		}

		/**
		 * Initialize the contents of the frame.
		 */
		private void initialize() {
			frame = new JFrame();
			frame.setBounds(100, 100, 609, 486);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(null);
			frame.setResizable(false);
		
			/**
			 * 欢迎标题
			 */
			JLabel lblNewLabel = new JLabel("欢迎回来",JLabel.CENTER);
			lblNewLabel.setBounds(30, 22, 201, 42);
			lblNewLabel.setFont(new Font("宋体", Font.BOLD, 36));
			lblNewLabel.setForeground(Color.red);
			frame.getContentPane().add(lblNewLabel);
			JLabel lblNewLabel_1 = new JLabel(bankClient.getUser().getUsername(),JLabel.CENTER); //欢迎标题用户名
			lblNewLabel_1.setBounds(30, 74, 201, 42);
			lblNewLabel_1.setFont(new Font("行书", Font.BOLD, 30));
			lblNewLabel_1.setForeground(Color.BLUE);
			frame.getContentPane().add(lblNewLabel_1);

			
			/*
			 * 退出按钮
			 */
			JButton btnExit = new JButton("退出");
			btnExit.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					System.exit(0);
				}
			});
			btnExit.setBounds(433, 340, 144, 80);
			frame.getContentPane().add(btnExit);
			
			JButton btnReLog = new JButton("注销");
			btnReLog.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					frame.setVisible(false);
					loginMenu.setVisible(true);
				}
			});
			btnReLog.setBounds(285, 340, 144, 80);
			frame.getContentPane().add(btnReLog);
			/**
			 * 菜单栏标签
			 */
			JLabel menuLab = new JLabel("菜单栏",JLabel.CENTER);
			menuLab.setForeground(Color.GREEN);
			menuLab.setBounds(285, 19, 292, 52);
			menuLab.setFont(new Font("宋体", Font.BOLD, 36));
			frame.getContentPane().add(menuLab);
			
			/**
			 * 死期 功能栏标签
			 */
			JLabel label = new JLabel("定期");
			label.setFont(new Font("宋体", Font.BOLD, 20));
			label.setBounds(407, 87, 50, 42);
			frame.getContentPane().add(label);
			
			/**
			 * 活期功能栏标签
			 */
			JLabel label_1 = new JLabel("活期");
			label_1.setFont(new Font("宋体", Font.BOLD, 20));
			label_1.setBounds(302, 87, 50, 42);
			frame.getContentPane().add(label_1);
			
			JButton button = new JButton("存半年");
			button.setBounds(386, 158, 93, 29);
			button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					String money = JOptionPane.showInputDialog("请输入你要存入的金额");
					if(money==null||money.equals("")) {
						return;
					}
					if(Double.parseDouble(money) < 0)
					{
						JOptionPane.showMessageDialog(null,"逗我呢？","消息",JOptionPane.ERROR_MESSAGE);
						return;
					}

					User user = bankClient.getUser(); //先更新一波数据
					user.addSavBal(Double.parseDouble(money), 0.5);
					try {
						if(bankClient.reqTransaction(user)) {
							JOptionPane.showMessageDialog(null,"存钱成功","消息", JOptionPane.INFORMATION_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(null,"存取失败","消息",JOptionPane.ERROR_MESSAGE);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			});
			frame.getContentPane().add(button);
			
			JButton btnNewButton = new JButton("存一年");
			btnNewButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					String money = JOptionPane.showInputDialog("请输入你要存入的金额");
					if(money==null||money.equals("")) {
						return;
					}
					if(Double.parseDouble(money) < 0)
					{
						JOptionPane.showMessageDialog(null,"逗我呢？","消息",JOptionPane.ERROR_MESSAGE);
						return;
					}

					User user = bankClient.getUser(); //先更新一波数据
					user.addSavBal(Double.parseDouble(money), 1);
					try {
						if(bankClient.reqTransaction(user)) {
							JOptionPane.showMessageDialog(null,"存钱成功","消息", JOptionPane.INFORMATION_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(null,"存取失败","消息",JOptionPane.ERROR_MESSAGE);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			});
			btnNewButton.setBounds(386, 197, 93, 29);
			frame.getContentPane().add(btnNewButton);
			
			JButton btnNewButton_1 = new JButton("存五年");
			btnNewButton_1.setBounds(386, 254, 93, 29);
			btnNewButton_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					String money = JOptionPane.showInputDialog("请输入你要存入的金额");
					if(money==null||money.equals("")) {
						return;
					}
					if(Double.parseDouble(money) < 0)
					{
						JOptionPane.showMessageDialog(null,"逗我呢？","消息",JOptionPane.ERROR_MESSAGE);
						return;
					}

					User user = bankClient.getUser(); //先更新一波数据
					user.addSavBal(Double.parseDouble(money), 5);
					try {
						if(bankClient.reqTransaction(user)) {
							JOptionPane.showMessageDialog(null,"存钱成功","消息", JOptionPane.INFORMATION_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(null,"存取失败","消息",JOptionPane.ERROR_MESSAGE);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			});
			frame.getContentPane().add(btnNewButton_1);
			
			JButton btnNewButton_2 = new JButton("取钱");
			btnNewButton_2.setBounds(386, 288, 93, 29);
			btnNewButton_2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
				
					String timestr = JOptionPane.showInputDialog("请输入你要从那个死期取[0.5,  1,  5]");
					if(timestr==null||timestr.equals("")) {
						return ;
					}
					double time = Double.parseDouble(timestr);
					int op = 0;
					if(time == 0.5)
						op = 0;
					else if(time == 1)
						op = 1;
					else if(time == 5)
						op = 2;
					else {JOptionPane.showMessageDialog(null,"没这种死期！！！","消息",JOptionPane.ERROR_MESSAGE);return;}
					String money = JOptionPane.showInputDialog("请输入你要取出的金额");	
					if(money==null||money.equals("")) {
						return;
					}
					if(Double.parseDouble(money) < 0)
					{
						JOptionPane.showMessageDialog(null,"逗我呢？","消息",JOptionPane.ERROR_MESSAGE);
						return;
					}
					double ins = bankClient.getUser().getSavingBals().get(op).getIns();
					DecimalFormat df = new DecimalFormat( "0.00");
					int result = JOptionPane.showConfirmDialog(null, "\"取钱将把您已经得到的利息(" + df.format(ins) + ")归零","提示",0);
					if(result == 1) {
						return;
					}
					User user = bankClient.getUser(); //先更新一波数据
					//user.drawSavBal(Double.parseDouble(money), time);
					try {
						if(user.drawSavBal(Double.parseDouble(money), time)&&bankClient.reqTransaction(user)) {
							JOptionPane.showMessageDialog(null,"取钱成功","消息", JOptionPane.INFORMATION_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(null,"取钱失败，请检查您的余额","消息",JOptionPane.ERROR_MESSAGE);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			});
			frame.getContentPane().add(btnNewButton_2);
			
			/**
			 * 活期存钱按钮
			 */
			JButton button_1 = new JButton("存钱");
			button_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					String money = JOptionPane.showInputDialog("请输入你要存入的金额");
					if(money==null||money.equals("")) {
						return;
					}
					if(Double.parseDouble(money) < 0)
					{
						JOptionPane.showMessageDialog(null,"逗我呢？","消息",JOptionPane.ERROR_MESSAGE);
						return;
					}
					User user = bankClient.getUser();
					user.addChkBal(Double.parseDouble(money));
					try {
						if(bankClient.reqTransaction(user)) {
							JOptionPane.showMessageDialog(null,"存钱成功","消息", JOptionPane.INFORMATION_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(null,"存取失败","消息",JOptionPane.ERROR_MESSAGE);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			});
			button_1.setBounds(285, 158, 93, 68);
			frame.getContentPane().add(button_1);
			/**
			 * 活期取钱
			 */
			JButton button_2 = new JButton("取钱");
			button_2.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					String money = JOptionPane.showInputDialog("请输入你要取出的金额");
					if(money==null||money.equals("")) {
						return;
					}
					if(Double.parseDouble(money) < 0)
					{
						JOptionPane.showMessageDialog(null,"逗我呢？","消息",JOptionPane.ERROR_MESSAGE);
						return;
					}
					User user = bankClient.getUser();
					try {
						bankClient.reqUsrInfo();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					if(user.getChkingBal().check() < Double.parseDouble(money))
					{
						JOptionPane.showMessageDialog(null,"余额不足","消息",JOptionPane.ERROR_MESSAGE);
					}
					else {
						user.drawChkBal(Double.parseDouble(money));
						try {
							if(bankClient.reqTransaction(user)) {
								JOptionPane.showMessageDialog(null,"取钱成功","消息", JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (Exception e) {
							e.printStackTrace();
						} 
					}
					
				}
			});
			button_2.setBounds(285, 253, 93, 66);
			frame.getContentPane().add(button_2);
			
			JLabel label_2 = new JLabel("贷款");
			label_2.setFont(new Font("宋体", Font.BOLD, 20));
			label_2.setBounds(504, 87, 50, 42);
			frame.getContentPane().add(label_2);
			/**
			 * 贷款按钮
			 */
			JButton button_3 = new JButton("贷款");
			button_3.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					String money = JOptionPane.showInputDialog("请输入你要贷款的金额");
					if(money==null||money.equals("")) {
						return;
					}
					if(Double.parseDouble(money) < 0)
					{
						JOptionPane.showMessageDialog(null,"逗我呢？","消息",JOptionPane.ERROR_MESSAGE);
						return;
					}
					User user = bankClient.getUser();
					try {
						bankClient.reqUsrInfo();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					if(user.getChkingBal().check() < Double.parseDouble(money))
					{
						JOptionPane.showMessageDialog(null,"信用不足","消息",JOptionPane.ERROR_MESSAGE);
					}else {
						try {
							user.addLoan(Double.parseDouble(money));
							if(bankClient.reqTransaction(user)) {
								JOptionPane.showMessageDialog(null,"贷款成功","消息", JOptionPane.INFORMATION_MESSAGE);
							}
						} catch (Exception e) {
							e.printStackTrace();
						} 
					}	
				}
			});
			button_3.setBounds(484, 158, 93, 68);
			frame.getContentPane().add(button_3);
			
			JButton btnNewButton_3 = new JButton("还款");
			btnNewButton_3.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					String money = JOptionPane.showInputDialog("请输入你要归还的金额\n（多余的钱自动存到活期账户）");
					if(money==null||money.equals("")) {
						return;
					}
					User user = bankClient.getUser();
					try {
						bankClient.reqUsrInfo();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					if(Double.parseDouble(money) < 0)
					{
						JOptionPane.showMessageDialog(null,"逗我呢？","消息",JOptionPane.ERROR_MESSAGE);
					}else {
						 if (Double.parseDouble(money) > user.getLoanBal()){
								user.addLoan(- user.getLoanBal());  //债务归零
								double m = Double.parseDouble(money)-user.getLoanBal();
								user.addChkBal(m);
						 }
						 else {
							 user.addLoan(- Double.parseDouble(money));  
						 }
						 try {
								if(bankClient.reqTransaction(user)) {
									JOptionPane.showMessageDialog(null,"还款成功","消息", JOptionPane.INFORMATION_MESSAGE);
								}
							} catch (Exception e) {
								e.printStackTrace();
							} 
					}
				
				}
			});
			btnNewButton_3.setBounds(484, 257, 93, 60);
			frame.getContentPane().add(btnNewButton_3);
			
			/**
			 * 查询余额贷款等信息的按钮
			 */
			JButton button_4 = new JButton("查询");
			button_4.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					try {
						bankClient.reqUsrInfo();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            DecimalFormat df = new DecimalFormat( "0.00"); 
					JOptionPane.showMessageDialog(null,
							"活期账户："+df.format(bankClient.getUser().getChkingBal().check())+ "\n"
							+ "亏钱债务："+df.format(bankClient.getUser().getLoanBal())+"\n"
							+ "半年定期本金："+df.format(bankClient.getUser().getSavingBals().get(0).getBal())+"\n"
							+ "半年定期利息："+df.format(bankClient.getUser().getSavingBals().get(0).getIns())+"\n"
							+ "一年定期本金："+df.format(bankClient.getUser().getSavingBals().get(1).getBal())+"\n"
							+ "一年定期利息："+df.format(bankClient.getUser().getSavingBals().get(1).getIns())+"\n"
							+ "五年定期本金："+df.format(bankClient.getUser().getSavingBals().get(2).getBal())+"\n"
							+ "五年定期利息："+df.format(bankClient.getUser().getSavingBals().get(2).getIns())+"\n",
							"消息", JOptionPane.INFORMATION_MESSAGE);
				}
			});
			button_4.setBounds(30, 347, 81, 66);
			frame.getContentPane().add(button_4);
			
			JLabel label_3 = new JLabel("银行信息列表",JLabel.CENTER);
			label_3.setFont(new Font("宋体", Font.BOLD, 20));
			label_3.setBounds(30, 126, 201, 42);
			frame.getContentPane().add(label_3);
			/**
			 * 银行信息显示
			 */
			table = new JTable(6,2);
			TableModel module = table.getModel();
			module.setValueAt("银行名称", 0, 0);
			module.setValueAt("东大银行", 0, 1);
			module.setValueAt("活期利率", 1, 0);
			module.setValueAt(BankInfo.cheingIns*100 + "%", 1, 1);
			module.setValueAt("半年定期利率", 2, 0);
			module.setValueAt(BankInfo.savingIns_Half*100 + "%", 2, 1);
			module.setValueAt("一年定期利率", 3, 0);
			module.setValueAt(BankInfo.savingIns_One*100 + "%", 3, 1);
			module.setValueAt("五年定期利率", 4, 0);
			module.setValueAt(BankInfo.savingIns_Five*100 + "%", 4, 1);
			module.setValueAt("贷款利率", 5, 0);
			module.setValueAt(BankInfo.loanIns*100 + "%", 5, 1);
			table.setBounds(30, 189, 201, 95);
			table.setEnabled(false);
			frame.getContentPane().add(table);
			
			JLabel label_4 = new JLabel("个人信息");
			label_4.setFont(new Font("宋体", Font.BOLD, 20));
			label_4.setBounds(87, 279, 93, 42);
			frame.getContentPane().add(label_4);
			
			/**
			 * 修改密码的按钮
			 */
			JButton button_5 = new JButton("修改密码");
			button_5.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					String passwdOld = JOptionPane.showInputDialog("请输入你要归还的金额请输入你原来的密码");
					if(passwdOld==null||passwdOld.equals("")) {
						return;
					}
					if(passwdOld.hashCode() == bankClient.getUser().getPassword()) {
						String passwdNew1 = JOptionPane.showInputDialog("请输入你要归还的金额请输入你新的密码");
						if(passwdNew1==null||passwdNew1.equals("")) {
							return;
						}
						String passwdNew2 = JOptionPane.showInputDialog("确认再次输入新的密码");
						if(passwdNew2==null||passwdNew2.equals("")) {
							return;
						}
						if(!passwdNew1.equals(passwdNew2)) {
							JOptionPane.showMessageDialog(null,"两次输入不同","消息", JOptionPane.ERROR_MESSAGE);
							return;
						}
						bankClient.getUser().setPassword(passwdNew1.hashCode());
						
						
						try {
							if(bankClient.reqTransaction(bankClient.getUser())){
							JOptionPane.showMessageDialog(null,"修改成功","消息", JOptionPane.INFORMATION_MESSAGE);
						}
						
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else {
						JOptionPane.showMessageDialog(null,"密码不正确，无权修改","消息", JOptionPane.ERROR_MESSAGE);
					}
//					String money = JOptionPane.showInputDialog("请输入你要归还的金额\n（多余的钱自动存到活期账户）");
//					if(money==null||money.equals("")) {
//						return;
//					}
//					User user = bankClient.getUser();
//					try {
//						bankClient.reqUsrInfo();
//					} catch (Exception e1) {
//						e1.printStackTrace();
//					}
//					if(Double.parseDouble(money) < 0)
//					{
//						JOptionPane.showMessageDialog(null,"逗我呢？","消息",JOptionPane.ERROR_MESSAGE);
//					}else {
//						 if (Double.parseDouble(money) > user.getLoanBal()){
//								user.addLoan(- user.getLoanBal());  //债务归零
//								double m = Double.parseDouble(money)-user.getLoanBal();
//								user.addChkBal(m);
//						 }
//						 else {
//							 user.addLoan(- Double.parseDouble(money));  
//						 }
//						 try {
//								if(bankClient.reqTransaction(user)) {
//									JOptionPane.showMessageDialog(null,"还款成功","消息", JOptionPane.INFORMATION_MESSAGE);
//								}
//							} catch (Exception e) {
//								e.printStackTrace();
//							} 
//					}
				
				}
			});
			button_5.setBounds(150, 347, 81, 66);
			frame.getContentPane().add(button_5);
		}
	}

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		 java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	        		try {
	        			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
						bankClient = new BankClient();
		        		loginMenu = new LoginMenu();

		        		loginMenu.setVisible(true);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
	
	            }
	        });
		}
}
