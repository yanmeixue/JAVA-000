package pers.ryan.gateway;

import lombok.extern.slf4j.Slf4j;
import pers.ryan.gateway.inbound.HttpInboundServer;

@Slf4j
public class NettyServerApplication {

    public final static String GATEWAY_NAME = "NIOGateway";
    public final static String GATEWAY_VERSION = "1.0.0";

    public static void main(String[] args) {
        String proxyServer = System.getProperty("proxyServer", "http://localhost:8801/");
        String proxyPort = System.getProperty("proxyPort", "8888");
        int port = Integer.parseInt(proxyPort);
        log.info("{} {} starting...", GATEWAY_NAME, GATEWAY_VERSION);
        HttpInboundServer server = new HttpInboundServer(port, proxyServer);
        try {
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
