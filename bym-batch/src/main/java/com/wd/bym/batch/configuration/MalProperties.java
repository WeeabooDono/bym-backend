package com.wd.bym.batch.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("mal")
@Getter
@Setter
public class MalProperties {

    private String clientId;
    private String clientHeader;
    private String url;
    private String apiVersion;
}
