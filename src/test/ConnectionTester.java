package test;

import java.util.concurrent.TimeUnit;

import log.Log;
import tcp.Connection;

public class ConnectionTester {
	/*
	 * Simulate the two boards sending messages with {tag:data} to the controller, which then sends it to the SQL server.
	 */
	public static void main(String[] args){
//		ConnectionServerTester tester1 = new ConnectionServerTester("Tester1", "127.0.0.1", 6262);
//		ConnectionServerTester tester2 = new ConnectionServerTester("Tester2", "127.0.0.1", 6363);
//		ConsumerTester tester3 = new ConsumerTester("tester3", tester1);
//		ConsumerTester tester4 = new ConsumerTester("tester4", tester2);
//		ConnectionServerTester tester5 = new ConnectionServerTester("Tester5", "127.0.0.1", 6464);
//		ConnectionServerTester tester6 = new ConnectionServerTester("Tester6", "127.0.0.1", 6565);
//		ConsumerTester tester7 = new ConsumerTester("tester7", tester5);
//		ConsumerTester tester8 = new ConsumerTester("tester8", tester6);
		
		
		
//		tester1.start();
//		tester2.start();
//		tester3.start();
//		tester4.start();
//		tester5.start();
//		tester6.start();
//		tester7.start();
//		tester8.start();
		
		
		Connection test = new Connection("derp", "127.0.0.1", 1337);
		Connection test1 = new Connection("derp", "127.0.0.1", 6161);
		Connection test2 = new Connection("derp", "127.0.0.1", 6262);
		test.start();
		test1.start();
		test2.start();
		test.putToQueue("{PDT:1}");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.putToQueue("{PDT:0}");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.putToQueue("{PLT:1}");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.putToQueue("{JIT:1}");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.putToQueue("{IsThisLegal:???}");
		

		
		
		
//		Connection connection = new Connection("", "127.0.0.1", 6161);
//		connection.start();
//		connection.putToQueue("HelloWorld");
//		
//		
//		Log.log(connection.takeFromQueue());
	}
}
