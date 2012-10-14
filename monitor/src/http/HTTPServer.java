package http;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandler;

public class HTTPServer {

	/**
	 * @param args
	 * @throws Exception
	 */
	public void run() throws Exception {
		Server server = new Server(8080);

		ContextHandler context = new ContextHandler();
		context.setContextPath("/subscriptions");
		server.setHandler(context);

		context.setHandler(new SubscriptionsHandler());

		server.start();

	}

}
