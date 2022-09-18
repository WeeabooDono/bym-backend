package com.wd.bym.webapp.controller;

import com.wd.bym.context.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .andExpect(status().isOk());
    }
}
