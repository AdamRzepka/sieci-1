package http;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import network.MessageQueue;

import org.mortbay.jetty.Request;
import org.mortbay.jetty.handler.AbstractHandler;

import sensors.Sensor;
import sensors.SensorDataCollector;
import subscription.Subscription;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubscriptionsHandler extends AbstractHandler {
	private SensorDataCollector sensorDataCollector;
	private MessageQueue messageQueue;
	private ArrayList<Subscription> subscriptions = new ArrayList<Subscription>();

	public SubscriptionsHandler(SensorDataCollector sensorDataCollectorInput, MessageQueue messageQueue) {
		this.sensorDataCollector = sensorDataCollectorInput;
		this.messageQueue = messageQueue;
	}

	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, int dispatch) throws IOException,
			ServletException {

		Pattern p = Pattern.compile("^/.[a-z]*/(.[\\d]*)$");
		Matcher m = p.matcher(request.getRequestURI());
		
		Pattern metricListPattern = Pattern.compile("^/.[a-z]*/metrics/(.[a-zA-Z0-9\\.]*)$");
		Matcher metricListMatcher = metricListPattern.matcher(request.getRequestURI());
		
		
		

		if (request.getRequestURI().equalsIgnoreCase("/subscriptions/")
				&& request.getMethod().equalsIgnoreCase("POST")) {
			// TODO: Poszedł post do servera
			
			String resource = request.getReader().readLine();
			String metric = request.getReader().readLine();
			Sensor sensor = null;
			sensor = sensorDataCollector.findSensor(resource, metric);
			
			Subscription subscription = new Subscription(sensorDataCollector.getMessageQueue(), sensor, sensorDataCollector);
			subscriptions.add(subscription);
			
			if(sensor != null){
				response.getWriter().println(
						subscription.getId() + "\n" +
						resource+"\n"+
						metric+"\n"+
						subscription.getPort()
						);
			}
			

			response.setContentType("text/plain");
			response.setStatus(HttpServletResponse.SC_CREATED);
			System.out.printf("Nowa subskrypcja o id %d utworzona\n", subscription.getId());
			// response.getWriter().println("Wpisać listę");
		} else if (request.getRequestURI().equalsIgnoreCase("/subscriptions/")
				&& request.getMethod().equalsIgnoreCase("GET")) {
			
			// Wypisz listę sensorów
			response.setContentType("text/plain");
			response.setStatus(HttpServletResponse.SC_OK);
			ArrayList<String> listResources = sensorDataCollector.listResources();
			
			for (String resource : listResources) {
				response.getWriter().println(resource);
			}
		} else if (m.find() && request.getMethod().equalsIgnoreCase("GET")) {
			// TODO: poszedł odpowiedni GET o numerze m.group(1)
			
			Subscription subscription = null;
			int id = Integer.parseInt(m.group(1));
			for (Subscription sub: subscriptions) {
				if (sub.getId() == id) {
					subscription = sub;
				}
			}
			
			if (subscription != null) {
				response.setContentType("text/plain");
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().println(subscription.getId() + "\n" +
						subscription.getSensor().getResource()+"\n"+
						subscription.getSensor().getMetric()+"\n"+
						subscription.getPort()
						);
			} else {
				response.setContentType("text/html");
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				response.getWriter().printf("<h1>Nie znaleziono subskrypcji o id %d</h1>", id);
			}

		} else if (m.find() && request.getMethod().equalsIgnoreCase("DELETE")) {
			Subscription subscription = null;
			int id = Integer.parseInt(m.group(1));
			for (Subscription sub: subscriptions) {
				if (sub.getId() == id) {
					subscription = sub;
				}
			}
			
			if (subscription != null) {
				subscriptions.remove(subscription);
				System.out.printf("Subskrypcja o id %d usunieta\n", id);
				response.setContentType("text/plain");
				response.setStatus(HttpServletResponse.SC_OK);
			}
			else {
				response.setContentType("text/html");
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				response.getWriter().printf("<h1>Nie znaleziono subskrypcji o id %d</h1>", id);
			}
		} else if (metricListMatcher.find() && request.getMethod().equalsIgnoreCase("GET")) {
			// TODO: poszedł odpowiedni GET o numerze metricListMatcher.group(1)
//			System.out.println();
			response.setContentType("text/plain");
			response.setStatus(HttpServletResponse.SC_OK);
			
			ArrayList<String> listMetrics = sensorDataCollector.listMetrics(metricListMatcher.group(1));
			
			for (String metric : listMetrics) {
				response.getWriter().println(metric);
			}

		}else {
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter().println("<h1>Nie znaleziono</h1>");
		}

		((Request) request).setHandled(true);
	}
}