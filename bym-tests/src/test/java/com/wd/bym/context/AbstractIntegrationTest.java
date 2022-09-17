package com.wd.bym.context;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@AutoConfigureMockMvc
public abstract class AbstractIntegrationTest extends AbstractAppContextTest {

    @Autowired
    private WebApplicationContext context;

    protected MockMvc mockMvc;

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    protected ResultMatcher verifyExceptionType(String exceptionType) {
        return MockMvcResultMatchers.jsonPath("$.exceptionType").value(exceptionType);
    }

    protected ResultMatcher verifyExceptionTitle(String title) {
        return MockMvcResultMatchers.jsonPath("$.title").value(title);
    }

    protected ResultMatcher verifyExceptionMessage(String message) {
        return MockMvcResultMatchers.jsonPath("$.message").value(message);
    }

    protected ResultMatcher verifyExceptionCause(String cause) {
        return MockMvcResultMatchers.jsonPath("$.exceptionMessage").value(cause);
    }

}
