package cn.server;

import java.io.IOException;
import java.sql.SQLException;

import cn.server.BankServer.ServerThread;

public class ServerMenu {
	public static void main(String[] args) throws ClassNotFoundException, IOException, SQLException {
		BankServer bankServer = new BankServer();

		try {
			while(true) {
				synchronized(bankServer) {
					ServerThread serverThread = bankServer.new ServerThread(bankServer.serverSocket.accept());
					serverThread.start();
				}
			}
		} catch (Exception e) {
			System.exit(1);
		}

	}
}
