package sensors;

import java.io.IOException;
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
				ServerSocketChannel serverChannel = (ServerSocketChannel) channel;
				try {
					SocketChannel socket = serverChannel.accept();
					System.out.printf("New connection from %s\n", socket
							.getRemoteAddress().toString());
					messageQueue.registerChannel(socket,
							new SensorMessageHandler(), SelectionKey.OP_READ);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.exit(1); // temporary
				}
			}
		}
	}

	private class SensorMessageHandler implements ChannelSelectionHandler {

		@Override
		public void onSelected(SelectableChannel channel,
				int readyOperationsMask) {
			if ((readyOperationsMask & SelectionKey.OP_READ) != 0) {
				SocketChannel socketChannel = (SocketChannel) channel;
				try {
					ByteBuffer buff = ByteBuffer.allocate(1024);
					int readed = socketChannel.read(buff);
					buff.flip();

					if (readed == -1) {
						System.out.printf("Client %s has disconnected\n",
								socketChannel.getRemoteAddress().toString());
						messageQueue.unregisterChannel(socketChannel);
						socketChannel.close();
					} else {
						System.out.printf("New message from %s:\n", socketChannel
								.getRemoteAddress().toString());
						CharBuffer cbuff = Charset.defaultCharset()
								.decode(buff);
						System.out.println(cbuff.toString());
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.exit(1); // temporary
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
			System.exit(1); // temporary
		}
	}

	private MessageQueue messageQueue;
}
