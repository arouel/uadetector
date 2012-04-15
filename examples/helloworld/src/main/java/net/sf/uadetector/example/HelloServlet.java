package net.sf.uadetector.example;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.uadetector.UADetectorServiceFactory;
import net.sf.uadetector.UserAgent;
import net.sf.uadetector.UserAgentStringParser;

public class HelloServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);

		PrintWriter out = response.getWriter();
		out.println("<h1>Hello Servlet</h1>");
		out.println("session=" + request.getSession(true).getId());
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
