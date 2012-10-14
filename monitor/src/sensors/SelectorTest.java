package sensors;

import http.HTTPServer;

import network.MessageQueue;

public class SelectorTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		
		MessageQueue queue = new MessageQueue();
		SensorDataCollector sensorDataCollector = new SensorDataCollector(queue);
		
		HTTPServer server = new HTTPServer();
		server.run(sensorDataCollector);
		
		
		queue.run();
		
	}

}
