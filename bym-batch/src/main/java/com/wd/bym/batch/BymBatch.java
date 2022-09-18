package com.wd.bym.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.wd.bym"})
public class BymBatch {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BymBatch.class);
        Environment env = app.run(args).getEnvironment();

        log.info("""

                        ---------------------------------------------------------
                        \tApplication '{}' is running!
                        ---------------------------------------------------------
                        
                 """,
                env.getProperty("spring.application.name"));
    }
}
