package net.sf.uadetector.example;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.uadetector.UADetectorServiceFactory;
import net.sf.uadetector.UserAgent;
import net.sf.uadetector.UserAgentStringParser;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class HelloWorld extends AbstractHandler {

	public static void main(final String[] args) throws Exception {
		final Server server = new Server(8080);
		server.setHandler(new HelloWorld());
		server.start();
		server.join();
	}

	@Override
	public void handle(final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);

		PrintWriter out = response.getWriter();
		out.println("<h1>Hello Servlet</h1>");
		out.println("<br>");

		// Get an UserAgentStringParser and analyze the requesting client
		UserAgentStringParser parser = UADetectorServiceFactory.getUserAgentStringParser();
		UserAgent agent = parser.parse(request.getHeader("User-Agent"));

		out.append("You're a <em>");
		out.append(agent.getName());
		out.append("</em> on <em>");
		out.append(agent.getOperatingSystem().getName());
		out.append("</em>!");
	}

}
