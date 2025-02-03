package com.uvg.sorting.utils;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.handler.HandlerList;

public class WebServer {
    public static void startServer() {
        Server server = new Server(8080);
        
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setWelcomeFiles(new String[]{ "index.html" });
        resource_handler.setResourceBase("src/main/webapp");
        
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { resource_handler });
        server.setHandler(handlers);
        
        try {
            server.start();
            System.out.println("Visualización disponible en: http://localhost:8080");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}