package connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//not a rate limiter
//sample implementation of a connection pool
public class ConnectionPool {
	private static final String URL = "database_url";
	private static final String USER = "username";
	private static final String PASSWORD = "password";
	private static final int POOL_SIZE = 5;
	
	private List<Connection> connectionPool;
	
	public ConnectionPool() {
		initializeConnectionPool();
	}
	
	private void initializeConnectionPool() {
		connectionPool = new ArrayList<>();
		for(int i=0; i < POOL_SIZE; i++) {
			try {
				Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
				connectionPool.add(connection);
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized Connection getConnection() {
		if(connectionPool.isEmpty()) {
			return createConnection();
		}
		Connection connection = connectionPool.remove(0);
		try {
			if(connection.isClosed()) {
				return getConnection(); 
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}	
		return connection;
	}
	
	private Connection createConnection() {
		try {
			return DriverManager.getConnection(URL,USER,PASSWORD);
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public synchronized void releaseConnection(Connection connection) {
		connectionPool.add(connection);
	}
	
	public void closeConnections() throws SQLException {
		for(Connection connection : connectionPool) {
			connection.close();
		}
		connectionPool.clear();
	}
}
