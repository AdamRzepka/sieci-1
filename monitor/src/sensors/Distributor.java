package sensors;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import network.ChannelSelectionHandler;
import network.MessageQueue;

/**
 * Klasa zajmująca się dystrybuowaniem pomiarów do zainteresowanych klientów
 * 
 * 
 */
public class Distributor implements ChannelSelectionHandler {

	public Distributor(MessageQueue messageQueue, Sensor sensor, int port ) {
		this.sensor = sensor;
		
		ServerSocketChannel channel;
		try {
			channel = ServerSocketChannel.open();
			channel.bind(new InetSocketAddress(port));
			messageQueue.registerChannel(channel, this, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1); // temporary
		}
		
	}

	void update() {
		String msg = createMessage(sensor);
		ByteBuffer buff = ByteBuffer.wrap(msg.getBytes());
		for (SocketChannel socket : clients) {
			try {
				socket.write(buff);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1); // temporary
			}
		}
	}

	@Override
	public void onSelected(SelectableChannel channel, int readyOperationsMask) {
		if ((readyOperationsMask & SelectionKey.OP_ACCEPT) != 0) {
			ServerSocketChannel serverChannel = (ServerSocketChannel) channel;
			try {
				SocketChannel socket = serverChannel.accept();
				System.out.printf("New connection from client at %s\n", socket
						.getRemoteAddress().toString());

				clients.add(socket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.exit(1); // temporary
			}
		}
	}

	private String createMessage(Sensor sensor) {
		String msg = "<measurement resourceId=\"%s\" metric=\"%s\">\n"
				+ "<timestamp>%s</timestamp>\n" + "<value>%f</value>\n"
				+ "</measurement>";
		return String.format(msg, sensor.getResource(), sensor.getMetric(),
				DateFormat.getInstance().format(new Date()),
				sensor.getLastMeasurement());
	}

	private ArrayList<SocketChannel> clients;
	private Sensor sensor;
}
