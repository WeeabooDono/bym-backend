package com.wd.bym.batch.job.MmlMangaRetriever;

import com.wd.bym.batch.job.MmlMangaRetriever.condition.MmlMangaRetrieverJobLaunchCondition;
import com.wd.bym.batch.job.MmlMangaRetriever.reader.MalMangaItemReader;
import com.wd.bym.batch.job.common.CustomJobExecutionListener;
import com.wd.bym.batch.job.common.CustomStepExecutionListener;
import com.wd.bym.batch.service.MalService;
import com.wd.bym.batch.utils.BatchType;
import com.wd.bym.core.domain.Manga;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManagerFactory;

@Configuration
@Conditional(MmlMangaRetrieverJobLaunchCondition.class)
@RequiredArgsConstructor
public class MmlMangaRetrieverJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MalService malService;
    private final EntityManagerFactory entityManagerFactory;

    @Bean(name = "mmlMangaRetrieverJob")
    public Job job() {
        return this.jobBuilderFactory.get(BatchType.MML_MANGA_RETRIEVER_JOB.getJobName())
                .listener(this.jobExecutionListener())
                .flow(this.startStep())
                .next(this.getMangaStep())
                .build().build();
    }

    @Bean
    public Step startStep() {
        return this.stepBuilderFactory.get("startStep")
                .listener(stepExecutionListener())
                .tasklet((contribution, chunkContext) -> RepeatStatus.FINISHED)
                .build();
    }

    @Bean Step getMangaStep() {
        var writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return this.stepBuilderFactory.get("getMangaStep")
                .listener(stepExecutionListener())
                .<Manga, Manga>chunk(0)
                .reader(itemReader())
                .writer(writer)
                .build();
    }

    @Bean
    public JobExecutionListener jobExecutionListener() {
        return new CustomJobExecutionListener();
    }

    @Bean
    public StepExecutionListener stepExecutionListener() {
        return new CustomStepExecutionListener();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ItemReader<Manga> itemReader() {
        return new MalMangaItemReader(malService);
    }
}
