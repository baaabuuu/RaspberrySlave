package sql;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import log.Log;

public class SQLConnection {
	private String port						= "";
	private String REMOTE_DATABASE_USERNAME = "";
	private String DATABASE_USER_PASSWORD	= "";
	private String PUBLIC_DNS				= "";
	private String DATABASE					= "";
	private Connection con;
	PreparedStatement createDB;
	
	/**
	 * Creates connection to SQL Server.
	 * @throws SQLException
	 */
	public SQLConnection() throws SQLException {
		Log.log("Starting connectToDatabase");
		con = DriverManager.getConnection("jdbc:sqlserver://" + PUBLIC_DNS + 
				"\\SQLEXPRESS:" + port + ";databaseName=" + DATABASE, REMOTE_DATABASE_USERNAME, DATABASE_USER_PASSWORD);
		
		Log.log("Connection succesfull");
	}
	/**
	 * Not currently used.
	 * @throws SQLException
	 * @deprecated
	 */
	public void closeConnection() throws SQLException {
		Log.important("Closing SQL Connection");
		con.close();
	}
	/**
	 * Drops Addresstable and creates a new one with this devices IP Address.
	 * @throws SQLException
	 * @throws IOException
	 */
	public void setupAddressTable() throws SQLException, IOException {
		String sqlCreate;
		sqlCreate = "DROP TABLE Addresstable";
		Log.log(sqlCreate);
		con.createStatement().executeUpdate(sqlCreate);
		
		sqlCreate = "CREATE TABLE Addresstable (Address varchar(255))";
		Log.log(sqlCreate);
		con.createStatement().executeUpdate(sqlCreate);
		
		//Find the socket that has internet access and get its IP address.
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress("amazonaws.com", 80));
		String ip = socket.getLocalAddress()+"";
		socket.close();
		
		//Use line below to get local address, currently uploads local address for testing purposes.
		//socket.getLocalAddress().toString().substring(1)
		sqlCreate = "INSERT INTO Addresstable VALUES ('" + ip + "')";
		Log.log(sqlCreate);
		con.createStatement().executeUpdate(sqlCreate);
		
		socket.close();
	}
}
