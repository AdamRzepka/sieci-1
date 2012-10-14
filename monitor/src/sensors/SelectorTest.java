package sensors;
//import http.HTTPServer;

import network.MessageQueue;

public class SelectorTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
//		HTTPServer server = new HTTPServer();
//		server.run();
		
		MessageQueue queue = new MessageQueue();
		new SensorDataCollector(queue);
		queue.run();
		
	}

}
