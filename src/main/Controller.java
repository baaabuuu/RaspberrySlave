package main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import sql.SQLConnection;
import tcp.*;

public class Controller {
	
	private String boardAddress1;
	private String boardAddress2;
	private int boardPort1;
	private int boardPort2;
	
	private SQLConnection server;
	
	
	/**
	 * Creates the class which handles all other threads and the communication between connections.
	 * @param boardAddress1
	 * @param boardAddress2
	 * @param raspberryAddress
	 * @param boardPort
	 */
	public Controller(String boardAddress1, String boardAddress2, int boardPort1, int boardPort2){
		this.boardAddress1 = boardAddress1;
		this.boardAddress2 = boardAddress2;
		
		this.boardPort1 = boardPort1;
		this.boardPort2 = boardPort2;
	}
	/**
	 * Connects to the SQL Server, updates the addressTable and hosts a TCP connection.
	 * @throws InterruptedException 
	 */
	public void startServer() throws InterruptedException{
		
		try {
			server = new SQLConnection();
			server.setupAddressTable();
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ConnectionHost raspberryConnect1 = new ConnectionHost("RaspberryConnect1", 4242);
		Connection nitrogenConnect1 = new Connection("nitrogenConnect1", boardAddress1, boardPort1);
		Connection de1socConnect1 = new Connection("de1socConnect1", boardAddress2, boardPort2);
		Consumer RaspberryConsume1 = new Consumer("RaspberryConsume1", raspberryConnect1, this);
		ConsumerDouble doubleConsumer1 = new ConsumerDouble("doubleConsumer1", de1socConnect1, nitrogenConnect1, raspberryConnect1, this);
		
		raspberryConnect1.start();
		nitrogenConnect1.start();
		de1socConnect1.start();
		RaspberryConsume1.start();
		doubleConsumer1.start();
		
		TimeUnit.SECONDS.sleep(3);
		
		ConnectionHost raspberryConnect2 = new ConnectionHost("RaspberryConnect2", 4343);
		Connection nitrogenConnect2 = new Connection("nitrogenConnect2", boardAddress1, boardPort1+101);
		Connection de1socConnect2 = new Connection("de1socConnect2", boardAddress2, boardPort2+101);
		Consumer RaspberryConsume2 = new Consumer("RaspberryConsume2", raspberryConnect2, this);
		ConsumerDouble doubleConsumer2 = new ConsumerDouble("doubleConsumer2", de1socConnect2, nitrogenConnect2, raspberryConnect2, this);
		
		raspberryConnect2.start();
		nitrogenConnect2.start();
		de1socConnect2.start();
		RaspberryConsume2.start();
		doubleConsumer2.start();
		
		TimeUnit.SECONDS.sleep(3);
		
		ConnectionHost raspberryConnect3 = new ConnectionHost("RaspberryConnect3", 4444);
		Connection nitrogenConnect3 = new Connection("nitrogenConnect3", boardAddress1, boardPort1+202);
		Connection de1socConnect3 = new Connection("de1socConnect3", boardAddress2, boardPort2+202);
		Consumer RaspberryConsume3 = new Consumer("RaspberryConsume3", raspberryConnect3, this);
		ConsumerDouble doubleConsumer3 = new ConsumerDouble("doubleConsumer3", de1socConnect3, nitrogenConnect3, raspberryConnect3, this);
		
		raspberryConnect3.start();
		nitrogenConnect3.start();
		de1socConnect3.start();
		RaspberryConsume3.start();
		doubleConsumer3.start();
	}
}
