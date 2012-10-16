import http.HTTPServer;
import network.MessageQueue;
import sensors.SensorDataCollector;


public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		MessageQueue queue = new MessageQueue();
		SensorDataCollector sensorDataCollector = new SensorDataCollector(queue);
		
		HTTPServer server = new HTTPServer();
		server.run(sensorDataCollector, queue);
		
		
		queue.run();
	}

}
