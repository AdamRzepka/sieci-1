package sensors;
import java.io.IOException;

import network.MessageQueue;

public class SelectorTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		MessageQueue queue = new MessageQueue();
		new SensorDataCollector(queue);
		queue.run();
	}

}
