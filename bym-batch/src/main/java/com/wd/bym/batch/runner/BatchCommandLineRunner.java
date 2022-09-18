package com.wd.bym.batch.runner;

import com.wd.bym.batch.exception.BatchException;
import com.wd.bym.batch.exception.BatchRunException;
import com.wd.bym.batch.exception.NoSuchBatchException;
import com.wd.bym.batch.service.BatchService;
import com.wd.bym.batch.transform.dto.JobExecutionDTO;
import com.wd.bym.batch.utils.BatchCommandLineArg;
import com.wd.bym.batch.utils.BatchType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.support.ExitCodeMapper;
import org.springframework.batch.core.launch.support.SimpleJvmExitCodeMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "bym.batch", name = "launcher.mode", havingValue = "command-line", matchIfMissing = true)
@RequiredArgsConstructor
public class BatchCommandLineRunner implements CommandLineRunner {

    private final ConfigurableApplicationContext context;
    private final BatchService batchService;

    @Override
    public void run(String... args) {
        try {
            int exitCode = execute(args);
            System.exit(SpringApplication.exit(context, () -> exitCode));
        } catch (BatchRunException e) {
            log.error(e.getMessage(), e);
            System.exit(SpringApplication.exit(context, () -> 1));
        }
    }

    private int execute(String... args) throws BatchRunException {
        SimpleCommandLinePropertySource simpleCommandLinePropertySource = new SimpleCommandLinePropertySource(args);
        String batchName = simpleCommandLinePropertySource.getProperty(BatchCommandLineArg.BATCH_NAME.getArgName());
        ExitCodeMapper exitCodeMapper = new SimpleJvmExitCodeMapper();

        JobExecutionDTO jobExecution;
        try {
            if (batchName != null && batchName.length() != 0) {
                jobExecution = this.batchService.executeBatch(BatchType.getByJobName(batchName), new JobParameters());
            } else {
                throw new BatchRunException(MessageFormat.format(
                        "You MUST enter one parameter: batch name argument is required => --{0}",
                        BatchCommandLineArg.BATCH_NAME.getArgName()));
            }
        } catch (BatchException | NoSuchBatchException e) {
            throw new BatchRunException(e.getMessage(), e);
        }
        return exitCodeMapper.intValue(jobExecution.getExitCode());
    }
}
