package tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

import log.Log;

public class Connection extends Thread {
	String transmitterName;
	int portNumber;
	Thread transmitter;
	String server;
	String inbound;
	
	ArrayBlockingQueue<String> inboundQueue = new ArrayBlockingQueue<String>(20);
	ArrayBlockingQueue<String> outbound = new ArrayBlockingQueue<String>(20);
	
	/**
	 * Creates thread handling TCP Connection, using an inbound and outbound queue.
	 * @param transmitterName
	 * @param server
	 * @param portNumber
	 */
	public Connection(String transmitterName, String server, int portNumber) {
		this.transmitterName = transmitterName;
		this.portNumber = portNumber;
		this.server = server;
		System.out.println("Thread created: " + transmitterName);
	}
	/**
	 * Connects to specified host.
	 * Everything it receives from host is put into inboundQueue.
	 * Everything in the outbound queue is sent to host.
	 */
	public void run(){
		while(true) {
			try{
				System.out.println("Attempting to Connect");
				Socket sock = new Socket(server, portNumber);
				
				System.out.println(sock.toString());
				BufferedReader buffRead = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				OutputStream out = sock.getOutputStream();
				OutputStreamWriter outW = new OutputStreamWriter(out);
				BufferedWriter outBW = new BufferedWriter(outW);
				
				//The send/receive loop.
				while(sock.isConnected()) {
					if (!outbound.isEmpty()) {
						try {
							String a = outbound.take();
							outBW.write(a);
							Log.log(a);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						outBW.flush();
					}
					if (buffRead.ready()) {
						try {
							inboundQueue.put(buffRead.readLine());
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				sock.close();
			
			} catch(IOException e){
				System.out.println(e);
			}
		}
	}
	/**
	 * Puts data into queue. Everything in this queue is sent to the connected host.
	 * @param toQueue
	 */
	public void putToQueue(String toQueue)
	{
		try {
			outbound.put(toQueue + "\r\n");
		} catch (InterruptedException e) {
			Log.log("failed to put data into queue.");
		}
	}
	/**
	 * Takes received data from queue.
	 * @return String
	 */
	public String takeFromQueue(){
		String get = "";
		try {
			if (!inboundQueue.isEmpty()) {
				get = inboundQueue.take();
			}
			} catch (InterruptedException e) {
				Log.log("failed to get data from queue.");
				get = "Failed to get data from queue.";
			}
		return get;
	}
}