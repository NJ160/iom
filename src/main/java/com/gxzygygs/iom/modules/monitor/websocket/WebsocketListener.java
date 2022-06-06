package com.gxzygygs.iom.modules.monitor.websocket;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Observable;
import java.util.Observer;

@Slf4j
public class WebsocketListener implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        log.error("Websocket线程down掉了！！！！！");
        new SimpleServer(new InetSocketAddress("localhost", 8887)).run();
    }
}
