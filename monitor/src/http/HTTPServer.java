package http;

import network.MessageQueue;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandler;

import sensors.SensorDataCollector;

public class HTTPServer {

	/**
	 * @param args
	 * @throws Exception
	 */
	public void run(SensorDataCollector sensorDataCollector, MessageQueue messageQueue) throws Exception {
		Server server = new Server(8080);

		ContextHandler context = new ContextHandler();
		context.setContextPath("/subscriptions");
		server.setHandler(context);

		context.setHandler(new SubscriptionsHandler(sensorDataCollector, messageQueue));

		server.start();

	}

}
