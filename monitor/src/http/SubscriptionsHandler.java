package http;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.Request;
import org.mortbay.jetty.handler.AbstractHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubscriptionsHandler extends AbstractHandler {

	public SubscriptionsHandler() {
	}

	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, int dispatch) throws IOException,
			ServletException {

		Pattern p = Pattern.compile("^/.[a-z]*/(.[\\d]*)$");
		Matcher m = p.matcher(request.getRequestURI());

		if (request.getRequestURI().equalsIgnoreCase("/subscriptions/")
				&& request.getMethod().equalsIgnoreCase("POST")) {
			// TODO: Poszedł post do servera

			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_CREATED);
			// response.getWriter().println("Wpisać listę");
		} else if (request.getRequestURI().equalsIgnoreCase("/subscriptions/")
				&& request.getMethod().equalsIgnoreCase("GET")) {
			// Wypisz listę sensorów
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			 response.getWriter().println("cpu-usage\n" +
			 "memory-usage\n");
		} else if (m.find() && request.getMethod().equalsIgnoreCase("GET")) {
			// TODO: poszedł odpowiedni GET o numerze m.group(1)
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);

		} else {
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter().println("<h1>Nie znaleziono</h1>");
		}

		((Request) request).setHandled(true);
	}
}