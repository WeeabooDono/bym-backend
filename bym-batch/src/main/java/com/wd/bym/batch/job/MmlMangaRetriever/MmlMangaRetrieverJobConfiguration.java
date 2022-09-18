package com.wd.bym.batch.job.MmlMangaRetriever;

import com.wd.bym.batch.job.MmlMangaRetriever.condition.MmlMangaRetrieverJobLaunchCondition;
import com.wd.bym.batch.job.common.CustomJobExecutionJobListener;
import com.wd.bym.batch.utils.BatchType;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@Conditional(MmlMangaRetrieverJobLaunchCondition.class)
@RequiredArgsConstructor
public class MmlMangaRetrieverJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean(name = "mmlMangaRetrieverJob")
    public Job job() {
        return this.jobBuilderFactory.get(BatchType.MML_MANGA_RETRIEVER_JOB.getJobName())
                .listener(this.jobExecutionListener())
                .flow(this.startStep())
                .build().build();
    }

    @Bean
    public Step startStep() {
        return this.stepBuilderFactory.get("startStep").tasklet((contribution, chunkContext) -> RepeatStatus.FINISHED).build();
    }

    @Bean
    public JobExecutionListener jobExecutionListener() {
        return new CustomJobExecutionJobListener();
    }
}
