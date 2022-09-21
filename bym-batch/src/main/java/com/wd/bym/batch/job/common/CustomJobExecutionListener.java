package com.wd.bym.batch.job.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class CustomJobExecutionListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("BATCH STARTED");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("BATCH ENDED");
    }
}
