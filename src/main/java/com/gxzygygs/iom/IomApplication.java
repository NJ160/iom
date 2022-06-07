package com.gxzygygs.iom;

import com.gxzygygs.iom.generator.CodeGenerator;
import com.gxzygygs.iom.modules.monitor.websocket.SimpleServer;
import com.gxzygygs.iom.modules.monitor.websocket.WebsocketListener;
import org.java_websocket.server.WebSocketServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.oas.annotations.EnableOpenApi;

import java.net.InetSocketAddress;

@SpringBootApplication
@MapperScan("com.gxzygygs.iom.modules.sys.mapper")
@EnableOpenApi
public class IomApplication {

    public static void main(String[] args) {
        SpringApplication.run(IomApplication.class, args);
//        new SimpleServer(new InetSocketAddress("localhost", 8887)).run();
    }

}
