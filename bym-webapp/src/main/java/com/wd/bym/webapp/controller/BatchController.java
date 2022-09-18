package com.wd.bym.webapp.controller;

import com.wd.bym.batch.exception.BatchException;
import com.wd.bym.batch.exception.NoSuchBatchException;
import com.wd.bym.batch.service.BatchService;
import com.wd.bym.batch.transform.dto.JobExecutionDTO;
import com.wd.bym.batch.utils.BatchType;
import com.wd.bym.webapp.exception.NotFoundException;
import com.wd.bym.webapp.exception.TechnicalException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/batches")
public class BatchController {

    private final BatchService batchService;

    @PostMapping("/{jobName}")
    public ResponseEntity<JobExecutionDTO> executeBatch(@PathVariable String jobName) {
        try {
            return ResponseEntity.ok(batchService.executeBatch(BatchType.getByJobName(jobName), null));
        } catch (NoSuchBatchException e) {
            throw new NotFoundException(NotFoundException.NotFoundExceptionType.BATCH_NOT_FOUND, jobName);
        } catch (BatchException e) {
            throw new TechnicalException(TechnicalException.TechnicalExceptionType.BATCH_START_EXECUTION, e, jobName);
        }
    }
}
