package com.wd.bym.batch.service;

import com.wd.bym.batch.exception.BatchException;
import com.wd.bym.batch.utils.BatchType;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;

public interface BatchService {

    JobExecution executeBatch(BatchType batchType, JobParameters jobParameters) throws BatchException;
}
