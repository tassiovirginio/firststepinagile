package br.com.fa7.firststepinagile;

import org.apache.wicket.util.time.Duration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.fa7.firststepinagile.business.UserBusiness;

public class Start {
	
	final static Logger logger = LoggerFactory.getLogger(Start.class);
	
    public static void main(String[] args) throws Exception {
        int timeout = (int) Duration.ONE_HOUR.getMilliseconds();
        
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "9999";
        }

        Server server = new Server();
        SocketConnector connector = new SocketConnector();

        connector.setMaxIdleTime(timeout);
        connector.setSoLingerTime(-1);
        connector.setPort(Integer.valueOf(webPort));
        server.addConnector(connector);

        WebAppContext bb = new WebAppContext();
        bb.setServer(server);
        bb.setContextPath("/");
        bb.setWar("src/main/webapp");

        server.setHandler(bb);

        try {
        	logger.debug(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
            server.start();
            System.in.read();
            logger.debug(">>> STOPPING EMBEDDED JETTY SERVER");
            server.stop();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
