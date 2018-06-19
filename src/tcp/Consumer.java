package tcp;

import log.Log;
import main.Controller;

public class Consumer extends Thread {
	private String inbound;
	private String tag;
	private ConnectionHost connection;
	private String name;
	private Controller controller;
	
	/**
	 * Creates the consumer thread. It will consume messages from the queue of its given connection.
	 * @param name
	 * @param connection
	 * @param controller
	 */
	public Consumer(String name, ConnectionHost connection, Controller controller){
		this.connection = connection;
		this.name = name;
		this.controller = controller;
	}
	public void run(){
		while(true){
			//To spend less cpu than it needs to.
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.out.println("A Thread named: " + name + " has died!");
			}
			//Take from queue and check if it contains information
			inbound = connection.takeFromQueue();
			if(inbound != ""){
				Log.log("Tagged info: " + inbound);
				
				//Remove encapsulation
				inbound = inbound.substring(inbound.indexOf("{")+1,inbound.indexOf(":")-1);
				
				//Get the tag and remove the tag from the message
				tag = inbound.substring(0,4);
				inbound = inbound.replace(tag, "");
				
				//Compare tag to a number of cases. Supposed to detect what tags are supposed to go to the Nitrogen6x
				//and which are supposed to go to the DE1-SOC, but is yet to be implemented.
				switch(tag){
				case "DAT:":
					break;
				case "SET:": 
					break;
				case "DIS:": 
					break;
				default: Log.log("message received!");
					break;
				}
			}
		}
	}
}
