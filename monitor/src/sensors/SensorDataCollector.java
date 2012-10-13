package sensors;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

import network.ChannelSelectionHandler;
import network.MessageQueue;

public class SensorDataCollector {
	private class NewConnectionHandler implements ChannelSelectionHandler {

		@Override
		public void onSelected(SelectableChannel channel,
				int readyOperationsMask) {
			if ((readyOperationsMask & SelectionKey.OP_ACCEPT) != 0) {
				ServerSocketChannel serverChannel = (ServerSocketChannel)channel;
				try {
					SocketChannel socket = serverChannel.accept();
					messageQueue.registerChannel(socket, new SensorMessageHandler(), SelectionKey.OP_READ);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private class SensorMessageHandler implements ChannelSelectionHandler {

		@Override
		public void onSelected(SelectableChannel channel,
				int readyOperationsMask) {
			if ((readyOperationsMask & SelectionKey.OP_READ) != 0) {
				SocketChannel socketChannel = (SocketChannel)channel;
				try {
//					InputStream stream =  socketChannel.socket().getInputStream();
//					InputStreamReader streamReader = new InputStreamReader(stream);
//					CharBuffer buff = CharBuffer.allocate(1024);
//					streamReader.read(buff);
					ByteBuffer buff = ByteBuffer.allocate(1024);
					socketChannel.read(buff);
					buff.flip();
					CharBuffer cbuff = Charset.defaultCharset().decode(buff);
					System.out.println(cbuff.toString());
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	private static final int PORT = 12087;

	SensorDataCollector(MessageQueue messageQueue) {
		this.messageQueue = messageQueue;
		ServerSocketChannel serverChannel;
		try {
			serverChannel = ServerSocketChannel.open();
			serverChannel.bind(new InetSocketAddress(SensorDataCollector.PORT));
			messageQueue.registerChannel(serverChannel,
					new NewConnectionHandler(), SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private MessageQueue messageQueue;
}
