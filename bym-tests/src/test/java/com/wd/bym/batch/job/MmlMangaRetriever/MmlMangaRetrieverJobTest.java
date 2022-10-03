package com.wd.bym.batch.job.MmlMangaRetriever;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.wd.bym.context.AbstractBatchContextTest;
import com.wd.bym.core.domain.Manga;
import com.wd.bym.core.repository.MangaRepository;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBatchTest
@SpringBootTest(args = "--batch.name=mmlMangaRetrieverJob")
public class MmlMangaRetrieverJobTest extends AbstractBatchContextTest {

    @Autowired
    public JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    public JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    public MangaRepository mangaRepository;

    @Value("classpath:json/mal-manga-response-page-1.json")
    public Resource malMangaResponsePageOne;

    @Value("classpath:json/mal-manga-response-page-2.json")
    public Resource malMangaResponsePageTwo;

    @Value("classpath:json/mal-manga-response-page-3.json")
    public Resource malMangaResponsePageThree;

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    static void registerMalProperties(DynamicPropertyRegistry registry) {
        registry.add("mal.url", () -> wireMockServer.baseUrl());
    }

    @Test
    public void testCommandLineArguments() {
        var optionNames = args.getOptionNames();
        var optionValues = args.getOptionValues("batch.name");
        assertThat(optionNames).containsOnly("batch.name");
        assertThat(optionValues).containsOnly("mmlMangaRetrieverJob");
    }

    @Test
    void mmlMangaRetrieverJob_launchJob() throws Exception {
        // check initial results
        assertThat(mangaRepository.findAll().size()).isEqualTo(0);
        this.initEndpoints();
        // launch job
        var jobParameters = jobLauncherTestUtils.getUniqueJobParameters();
        var jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // check repository results
        var mangas = mangaRepository.findAll();
        assertThat(mangas.size()).isEqualTo(8);
        assertThat(mangas)
                .extracting(Manga::getId, Manga::getTitle)
                .contains(
                        tuple(1L, "Berserk"),
                        tuple(2L, "JoJo no Kimyou na Bouken Part 7: Steel Ball Run"),
                        tuple(3L, "One Piece"),
                        tuple(4L, "Vagabond"),
                        tuple(5L, "Monster"),
                        tuple(6L, "Slam Dunk"),
                        tuple(7L, "Fullmetal Alchemist"),
                        tuple(8L, "Oyasumi Punpun"));

        // check job step calls
        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(2);
        assertThat(jobExecution.getStepExecutions())
                .extracting(StepExecution::getStepName)
                .containsExactly("startStep", "getMangaStep");

        // check job result
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
    }

    private void initEndpoints() throws IOException {
        // Page 1
        wireMockServer.stubFor(get("/v1/manga/ranking?ranking_type=all&offset=0&limit=500&fields=title")
                .withQueryParams(Map.of(
                        "ranking_type", equalTo("all"),
                        "offset", equalTo("0"),
                        "limit", equalTo("500"),
                        "fields", equalTo("title")
                ))
                .willReturn(okJson(ressourceToString(malMangaResponsePageOne))));

        // Page 2
        wireMockServer.stubFor(get("/v1/manga/ranking?ranking_type=all&offset=3&limit=3&fields=title")
                .withQueryParams(Map.of(
                        "ranking_type", equalTo("all"),
                        "offset", equalTo("3"),
                        "limit", equalTo("3"),
                        "fields", equalTo("title")
                ))
                .willReturn(okJson(ressourceToString(malMangaResponsePageTwo))));

        // Page 3
        wireMockServer.stubFor(get("/v1/manga/ranking?ranking_type=all&offset=6&limit=3&fields=title")
                .withQueryParams(Map.of(
                        "ranking_type", equalTo("all"),
                        "offset", equalTo("6"),
                        "limit", equalTo("3"),
                        "fields", equalTo("title")
                ))
                .willReturn(okJson(ressourceToString(malMangaResponsePageThree))));
    }

    private static String ressourceToString(Resource resource) throws IOException {
        return FileUtils.readFileToString(
                ResourceUtils.getFile(resource.getURL()),
                StandardCharsets.UTF_8);
    }

    @AfterEach
    void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }
}
