package com.money.transfer.main;

import com.money.transfer.rest.AccountRestServiceImpl;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EmbeddedServer {

    private static final Logger log = LoggerFactory.getLogger(EmbeddedServer.class);
    private static final int SERVER_PORT = 9001;

    private EmbeddedServer() {
    }

    public static void main(String[] args) throws Exception {
        log.info("Starting Server...");
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(SERVER_PORT);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames",
                AccountRestServiceImpl.class.getCanonicalName());

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
        log.info("Server started successfully...");
    }
}
