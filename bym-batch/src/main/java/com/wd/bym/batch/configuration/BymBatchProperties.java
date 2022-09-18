package com.wd.bym.batch.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("bym.batch")
@Getter
public class BymBatchProperties {

    private final BatchLauncherSettings launcher = new BatchLauncherSettings();

    @Getter
    @Setter
    public static class BatchLauncherSettings {
        private BatchLaunchMode mode = BatchLaunchMode.COMMAND_LINE;
    }

    public enum BatchLaunchMode {
        COMMAND_LINE,
        SERVICE
    }
}
