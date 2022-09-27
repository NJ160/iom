package com.gxzygygs.iom;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@MapperScan("com.gxzygygs.iom.modules.sys.mapper")
@EnableOpenApi
public class IomApplication {

    public static void main(String[] args) {
        SpringApplication.run(IomApplication.class, args);
//        new SimpleServer(new InetSocketAddress("localhost", 8887)).run();
    }

}
