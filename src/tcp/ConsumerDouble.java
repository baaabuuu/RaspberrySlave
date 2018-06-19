package tcp;

import main.Controller;

public class ConsumerDouble extends Thread {
	private Connection connection1;
	private Connection connection2;
	private ConnectionHost connection3;
	private String name;
	private Controller controller;
	
	private String incoming;
	
	/**
	 * Creates the consumer thread. It will consume messages from each queue of its given connections.
	 * @param name
	 * @param connection1
	 * @param connection2
	 * @param connection3
	 * @param controller
	 */
	public ConsumerDouble(String name, Connection connection1, Connection connection2, ConnectionHost connection3, Controller controller){
		this.connection1 = connection1;
		this.connection2 = connection2;
		this.connection3 = connection3;
		this.name = name;
		this.controller = controller;
	}
	/**
	 * Runs the thread
	 */
	public void run(){
		while(true){
			//To no use too much cpu.
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				System.out.println("A Thread named: " + name + " has died!");
			}
			//Take from queue of connection1 (either a nitrogen6x or de1-soc)
			incoming = connection1.takeFromQueue();
			if(incoming != ""){
				//Relay to other Raspberry Pi
				connection3.putToQueue(incoming);
			}
			
			//To no use too much cpu.
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				System.out.println("A Thread named: " + name + " has died!");
			}
			//Take from other connection
			incoming = connection2.takeFromQueue();
			if(incoming != ""){
				//Relay to other Raspberry Pi
				connection3.putToQueue(incoming);
			}
		}
	}
}