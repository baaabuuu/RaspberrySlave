package main;

import log.Log;

public class Main {
	//Starts the program
	public static void main(String[] args){
		//Create controller and start server hosting
		Controller controller = new Controller(null, null, 0, 0);
		try {
			controller.startServer();
		} catch (InterruptedException e) {
			Log.important("Something Interrupted the controller!");
		}
	}
	
}
