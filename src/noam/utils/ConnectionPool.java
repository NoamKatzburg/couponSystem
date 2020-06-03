package noam.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
	private static ConnectionPool instance = null;// new ConnectionPool();

	private final int maxConnections = 10;
	private int totalConnections = 0;
	private BlockingQueue<Connection> connections;

	private ConnectionPool() {
		System.out.println(connections);
		connections = createConnections();
		System.out.println(connections);
	}

	public Connection getConnection() {
		synchronized (connections) {
			if (connections.isEmpty()) {
				try {
					System.out.println("Please wait while we take care of your request");
					connections.wait();
				} catch (InterruptedException e) {
					System.out.println("Error: " + e.getMessage());
				}
			} else {
				try {
					return connections.take();
				} catch (InterruptedException e) {
					System.out.println("Error: " + e.getMessage());
				}
			}
			return null;
		}
	}

	public void returnConnection(Connection connection) throws InterruptedException {
		synchronized (connections) {
			if (connection != null) {
				connections.offer(connection);
				connections.notify();
			}
		}
	}

	public void closeAllConnections() {
		synchronized (connections) {
			while (!connections.isEmpty()) {
				if (connections.element() != null) {
					try {
						connections.element().close();
					} catch (SQLException e) {
						System.out.println("Error: " + e.getMessage());
					}
				}
				connections.remove();
			}
		}
	}

	public static ConnectionPool getInstance() {

		if (instance == null) {
			synchronized (ConnectionPool.class) {
				if (instance == null) {
					instance = new ConnectionPool();
				}
			}

		}
		return instance;

	}

	private int getMaxConnections() {
		return maxConnections;
	}

	private BlockingQueue<Connection> createConnections() {
		BlockingQueue<Connection> connect = new ArrayBlockingQueue<Connection>(getMaxConnections());
		while (connect.size() < 10) {
			try {
				System.out.println(connect.size());
				System.out.println("Trying");
				Connection con = DriverManager.getConnection(Database.getUrl(), Database.getUrl(),
						Database.getPassword());
				connect.offer(con);
			} catch (SQLException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
		return connect;
	}

}
