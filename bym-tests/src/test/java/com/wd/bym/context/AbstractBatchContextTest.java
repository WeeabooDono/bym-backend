package com.wd.bym.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = {"bym.batch.launcher.mode=command_line"})
public abstract class AbstractBatchContextTest extends AbstractAppContextTest {

    @Autowired
    protected ApplicationArguments args;
}
