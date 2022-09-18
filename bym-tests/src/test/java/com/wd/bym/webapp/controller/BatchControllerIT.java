package com.wd.bym.webapp.controller;

import com.wd.bym.context.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
                scripts = {"classpath:datasets/repository/batch_test_before.sql"}),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD,
                scripts = {"classpath:datasets/repository/batch_test_after.sql"}),
})
class BatchControllerIT extends AbstractIntegrationTest {

    private static final String ENDPOINT = "/api/v1/batches/";

    @Test
    void executeBatch_whenJobNameDoesNotExists_shouldReturnBadRequest() throws Exception {
        final MockHttpServletRequestBuilder request = post(ENDPOINT + "/test");

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(verifyExceptionType("com.wd.bym.webapp.exception.NotFoundException"))
                .andExpect(verifyExceptionTitle("error.server.not-found.batch.title"))
                .andExpect(verifyExceptionMessage("error.server.not-found.batch.msg"))
                .andExpect(verifyExceptionCause("test"));
    }

    @Test
    void executeBatch_whenJobNameIsMmlMangaRetrieverJob_shouldReturnBadRequest() throws Exception {
        final MockHttpServletRequestBuilder request = post(ENDPOINT + "/mmlMangaRetrieverJob");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.jobName").value("mmlMangaRetrieverJob"))
                .andExpect(jsonPath("$.status").value("COMPLETED"))
                .andExpect(jsonPath("$.exitCode").value("COMPLETED"))
                .andExpect(jsonPath("$.error").isEmpty())
                .andExpect(jsonPath("$.stepExecutions", hasSize(1)))
                .andExpect(jsonPath("$.stepExecutions[0].stepName").value("startStep"))
                .andExpect(jsonPath("$.stepExecutions[0].status").value("COMPLETED"))
                .andExpect(jsonPath("$.stepExecutions[0].exitCode").value("COMPLETED"))
                .andExpect(jsonPath("$.stepExecutions[0].readCount").value(0))
                .andExpect(jsonPath("$.stepExecutions[0].writeCount").value(0))
                .andExpect(jsonPath("$.stepExecutions[0].commitCount").value(1))
                .andExpect(jsonPath("$.stepExecutions[0].error").isEmpty())
        ;
    }
}
