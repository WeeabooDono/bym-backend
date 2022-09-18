package com.wd.bym.batch.service.impl;

import com.wd.bym.batch.exception.BatchException;
import com.wd.bym.batch.service.BatchService;
import com.wd.bym.batch.transform.dto.JobExecutionDTO;
import com.wd.bym.batch.transform.mapper.JobExecutionMapper;
import com.wd.bym.batch.utils.BatchType;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BatchServiceImpl implements BatchService {

    private final JobExplorer jobExplorer;
    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    private final JobExecutionMapper mapper;

    @Override
    public JobExecutionDTO executeBatch(BatchType batchType, JobParameters jobParameters) throws BatchException {
        JobExecution jobExecution;

        Set<JobExecution> jobExecutions = this.jobExplorer.findRunningJobExecutions(batchType.getJobName());
        if (!CollectionUtils.isEmpty(jobExecutions)) {
            // TODO: gérer le cas ou un batch est déjà lancé
        }
        jobExecution = executeJob(batchType.getJobName(), jobParameters);

        return mapper.toDTO(jobExecution);
    }

    private JobExecution executeJob(String jobName, JobParameters jobParameters) throws BatchException {
        JobExecution jobExecution;
        try {
            JobParametersBuilder jobParametersBuilder;
            if (jobParameters != null) {
                jobParametersBuilder = new JobParametersBuilder(jobParameters);
            } else {
                jobParametersBuilder = new JobParametersBuilder();
            }

            jobParametersBuilder.addLong("run.id", System.currentTimeMillis());
            jobExecution = jobLauncher.run(jobRegistry.getJob(jobName), jobParametersBuilder.toJobParameters());
        } catch (Exception e) {
            throw new BatchException(MessageFormat.format(
                    "An error occurred during the execution of the job [{0}] - {1}",
                    jobName,
                    e.getMessage()));
        }
        return jobExecution;
    }
}
