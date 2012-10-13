package sensors;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import network.MessageQueue;

public class SelectorTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		MessageQueue queue = new MessageQueue();
		SensorDataCollector collector = new SensorDataCollector(queue);
		queue.run();
	}

}
