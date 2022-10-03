package com.wd.bym.webapp.controller;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.wd.bym.context.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
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

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    static void registerMalProperties(DynamicPropertyRegistry registry) {
        registry.add("mal.url", () -> wireMockServer.baseUrl());
    }

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

        // initialize endpoints
        this.initEndpoints();

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.jobName").value("mmlMangaRetrieverJob"))
                .andExpect(jsonPath("$.status").value("COMPLETED"))
                .andExpect(jsonPath("$.exitCode").value("COMPLETED"))
                .andExpect(jsonPath("$.error").isEmpty())
                .andExpect(jsonPath("$.stepExecutions", hasSize(2)))
                .andExpect(jsonPath("$.stepExecutions[0].stepName").value("startStep"))
                .andExpect(jsonPath("$.stepExecutions[0].status").value("COMPLETED"))
                .andExpect(jsonPath("$.stepExecutions[0].exitCode").value("COMPLETED"))
                .andExpect(jsonPath("$.stepExecutions[0].readCount").value(0))
                .andExpect(jsonPath("$.stepExecutions[0].writeCount").value(0))
                .andExpect(jsonPath("$.stepExecutions[0].commitCount").value(1))
                .andExpect(jsonPath("$.stepExecutions[0].error").isEmpty())
                .andExpect(jsonPath("$.stepExecutions[1].stepName").value("getMangaStep"))
                .andExpect(jsonPath("$.stepExecutions[1].status").value("COMPLETED"))
                .andExpect(jsonPath("$.stepExecutions[1].exitCode").value("COMPLETED"))
                .andExpect(jsonPath("$.stepExecutions[1].readCount").value(2))
                .andExpect(jsonPath("$.stepExecutions[1].writeCount").value(2))
                .andExpect(jsonPath("$.stepExecutions[1].commitCount").value(3))
                .andExpect(jsonPath("$.stepExecutions[1].error").isEmpty());

    }

    private void initEndpoints() {

        // Page 1
        wireMockServer.stubFor(get("/v1/manga/ranking?ranking_type=all&offset=0&limit=500&fields=title")
                .withQueryParams(Map.of(
                        "ranking_type", equalTo("all"),
                        "offset", equalTo("0"),
                        "limit", equalTo("500"),
                        "fields", equalTo("title")
                ))
                .willReturn(okJson("""
                        {
                          "paging": {
                            "next": "https://api.myanimelist.net/v2/manga/ranking?offset=3&ranking_type=all&limit=3&fields=title"
                          }
                        }
                        """
                )));
        // Page 2
        wireMockServer.stubFor(get("/v1/manga/ranking?ranking_type=all&offset=3&limit=3&fields=title")
                .withQueryParams(Map.of(
                        "ranking_type", equalTo("all"),
                        "offset", equalTo("3"),
                        "limit", equalTo("3"),
                        "fields", equalTo("title")
                ))
                .willReturn(okJson("""
                        {
                          "paging": {}
                        }
                        """)));

    }
}
