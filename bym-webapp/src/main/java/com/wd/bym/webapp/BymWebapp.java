package com.wd.bym.webapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.wd.bym"})
public class BymWebapp {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(BymWebapp.class);
        Environment env = app.run(args).getEnvironment();

        String protocol = "http";
        String hostAddress = InetAddress.getLocalHost().getHostAddress();

        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }

        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (contextPath == null || contextPath.length() == 0) {
            contextPath = "/";
        }


        log.info("""

                        ---------------------------------------------------------
                        \tApplication '{}' is running! Access Urls:\s
                        \tLocal: \t\t{}://localhost:{}{}
                        \tExternal: \t{}://{}:{}{}
                        ---------------------------------------------------------
                        
                 """,
                env.getProperty("spring.application.name"),
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath);
    }
}
