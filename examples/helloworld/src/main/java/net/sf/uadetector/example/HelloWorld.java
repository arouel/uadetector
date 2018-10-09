/*******************************************************************************
 * Copyright 2012 André Rouél
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.uadetector.example;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.uadetector.ReadableUserAgent;
import net.sf.uadetector.UserAgentStringParser;
import net.sf.uadetector.service.UADetectorServiceFactory;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.google.gson.GsonBuilder;

public class HelloWorld extends AbstractHandler {

	public static void main(final String[] args) throws Exception {
		final Server server = new Server(8080);
		server.setHandler(new HelloWorld());
		server.start();
		server.join();
	}

	@Override
	public void destroy() {
		// undeploy used parsers cleanly
		UADetectorServiceFactory.getOnlineUpdatingParser().shutdown();
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
		UserAgentStringParser parser = UADetectorServiceFactory.getOnlineUpdatingParser();
		ReadableUserAgent agent = parser.parse(request.getHeader("User-Agent"));

		out.append("You're a <em>");
		out.append(agent.getName());
		out.append("</em> on <em>");
		out.append(agent.getOperatingSystem().getName());
		out.append("</em>!");
    
    out.println("<br/><pre>");
    out.append(new GsonBuilder().setPrettyPrinting().create().toJson(agent));
    out.println("</pre>");
	}

}
