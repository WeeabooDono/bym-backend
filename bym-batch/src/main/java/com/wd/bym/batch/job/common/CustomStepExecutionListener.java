package com.wd.bym.batch.job.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

@Slf4j
public class CustomStepExecutionListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("STEP STARTED");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("STEP ENDED");
        return stepExecution.getExitStatus();
    }
}
