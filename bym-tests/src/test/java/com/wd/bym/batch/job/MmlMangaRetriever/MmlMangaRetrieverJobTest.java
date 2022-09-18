package com.wd.bym.batch.job.MmlMangaRetriever;

import com.wd.bym.context.AbstractBatchContextTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(args = "--batch.name=mmlMangaRetrieverJob")
public class MmlMangaRetrieverJobTest extends AbstractBatchContextTest {

    @Autowired
    public JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    public JobRepositoryTestUtils jobRepositoryTestUtils;

    @Test
    public void testCommandLineArguments() {
        var optionNames = args.getOptionNames();
        var optionValues = args.getOptionValues("batch.name");
        assertThat(optionNames).containsOnly("batch.name");
        assertThat(optionValues).containsOnly("mmlMangaRetrieverJob");
    }

    @Test
    void mmlMangaRetrieverJob_launchJob() throws Exception {
        var jobParameters = jobLauncherTestUtils.getUniqueJobParameters();
        var jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        assertThat(jobExecution.getStepExecutions().size()).isEqualTo(1);
        assertThat(jobExecution.getStepExecutions())
                .extracting(StepExecution::getStepName)
                .containsExactly("startStep");
        assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
    }

    @AfterEach
    void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @TestConfiguration
    public static class SingleStepConfig {

        @Bean
        public JobLauncherTestUtils jobLauncherTestUtils() {
            return new JobLauncherTestUtils();
        }

        @Bean
        public JobRepositoryTestUtils jobRepositoryTestUtils() {
            return new JobRepositoryTestUtils();
        }
    }
}
