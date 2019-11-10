package com.example.demo.rover;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.example.demo.rover.servlet.CommandServlet;

public class JettyServer {
	static Server server = new Server();

	public static void start(String[] args) {
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

		ServerConnector connector = new ServerConnector(server);
        connector.setPort(Integer.valueOf(args[1]));
        server.setConnectors(new Connector[] {connector});

		server.setHandler(context);
		
		context.addServlet(new ServletHolder(new CommandServlet()), "/command");
				try {
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void stop(String[] args) {
		System.out.println("stop");
		try {
			server.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws InterruptedException {
//		if ("start".equals(args[0])) {
			start(args);
//		} else if ("stop".equals(args[0])) {
//
//			stop(args);
//		}

	}

}
