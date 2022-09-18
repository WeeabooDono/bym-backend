package com.wd.bym.batch.transform.mapper.decorators;

import com.wd.bym.batch.transform.dto.JobExecutionDTO;
import com.wd.bym.batch.transform.mapper.JobExecutionMapper;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

public class JobExecutionMapperDecorator implements JobExecutionMapper {

    @Autowired
    @Qualifier("delegate")
    private JobExecutionMapper delegate;

    @Override
    public JobExecutionDTO toDTO(JobExecution jobExecution) {
        var dto = delegate.toDTO(jobExecution);
        if (dto != null && dto.getEndTime() != null && dto.getStartTime() != null) {
            dto.setDurationInMs(Objects.requireNonNull(jobExecution.getEndTime()).getTime() -
                    Objects.requireNonNull(jobExecution.getStartTime()).getTime());
        }
        return dto;
    }

    @Override
    public List<JobExecutionDTO> toDTOs(List<JobExecution> jobExecutions) {
        List<JobExecutionDTO> dtos = null;
        if (!CollectionUtils.isEmpty(jobExecutions)) {
            dtos = jobExecutions.stream().map(this::toDTO).toList();
        }
        return dtos;
    }
}
