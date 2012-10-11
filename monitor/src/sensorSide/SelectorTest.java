package sensorSide;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class SelectorTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Selector selector = Selector.open();
		ServerSocketChannel serverSocket = ServerSocketChannel.open();
		System.out.println("ServerSocketChannel open");
		serverSocket.configureBlocking(false);
		serverSocket.bind(new InetSocketAddress(12087));
		System.out.println("ServerSocketChannel binded");
		SelectionKey serverKey = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
		
		System.out.println("ServerSocketChannel selecting...");
		selector.select();
		System.out.println("ServerSocketChannel selected");
		SocketChannel socket = serverSocket.accept();
		System.out.println("New connection accepted");
		socket.configureBlocking(false);
		SelectionKey socketKey = socket.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		
		System.out.println("Selecting...");
		selector.select();
		System.out.println("Selected");
		if (socketKey.isReadable())
		{
			System.out.println("Readable!");
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			socket.read(buffer);
			buffer.flip();
			String message = Charset.forName("US-ASCII").decode(buffer).toString();
			System.out.printf("Message: %s,\nSending back...\n", message);
			socket.write(ByteBuffer.wrap(String.format("Sending back: %s", message).getBytes()));
			System.out.println("Message sent back. Closing connection...");
			socket.close();
		}
		serverSocket.close();
		
	}

}
