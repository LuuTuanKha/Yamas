package connectDATA;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect_Data {
	public static Connection con = null;
	private static Connect_Data instance = new Connect_Data();
	public static Connect_Data getInstance() {
		return instance;
	}
	
	public void connect() throws SQLException {
		String url = "jdbc:sqlserver://localhost:1433;databasename=QLXeMay";
		con = DriverManager.getConnection(url, "sa","sapassword");
		
		
	}
		
	public void disconnect() {
		if(con!= null)
			try {
				con.close();
			}
		catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
	}
	public static Connection getConnection() {
		return con;
		
	}
	
}
