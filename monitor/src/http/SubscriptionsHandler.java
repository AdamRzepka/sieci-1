package http;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.Request;
import org.mortbay.jetty.handler.AbstractHandler;

import sensors.Subscription;
import sensors.Sensor;
import sensors.SensorDataCollector;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubscriptionsHandler extends AbstractHandler {
	private SensorDataCollector sensorDataCollector;

	public SubscriptionsHandler(SensorDataCollector sensorDataCollectorInput) {
		this.sensorDataCollector = sensorDataCollectorInput;
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
			
			Subscription distributor = new Subscription(sensorDataCollector.getMessageQueue(), sensor, sensorDataCollector);
			
			

			
			
			if(sensor != null){
				response.getWriter().println(
						resource+"\n"+
						metric+"\n"+
						distributor.getPort()
						);
			}
			

			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_CREATED);
			// response.getWriter().println("Wpisać listę");
		} else if (request.getRequestURI().equalsIgnoreCase("/subscriptions/")
				&& request.getMethod().equalsIgnoreCase("GET")) {
			
			// Wypisz listę sensorów
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			ArrayList<String> listResources = sensorDataCollector.listResources();
			
			for (String resource : listResources) {
				response.getWriter().println(resource);
			}
		} else if (m.find() && request.getMethod().equalsIgnoreCase("GET")) {
			// TODO: poszedł odpowiedni GET o numerze m.group(1)
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);

		} else if (metricListMatcher.find() && request.getMethod().equalsIgnoreCase("GET")) {
			// TODO: poszedł odpowiedni GET o numerze metricListMatcher.group(1)
//			System.out.println();
			response.setContentType("text/html");
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