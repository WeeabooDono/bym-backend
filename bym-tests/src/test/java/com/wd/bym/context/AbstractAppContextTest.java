package com.wd.bym.context;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = {ApplicationTest.class})
@TestPropertySource("classpath:config/application.yml")
public abstract class AbstractAppContextTest {
}
