package com.hidewnd.sofademo.demos.socket.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "socket")
public class SockProperties {

    private Integer port = 5555;

    private String path = "/ws";

    private Integer boss = 2;

    private Integer worker = 2;
}
