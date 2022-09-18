package com.wd.bym.batch.transform.mapper.decorators;

import com.wd.bym.batch.transform.dto.StepExecutionDTO;
import com.wd.bym.batch.transform.mapper.StepExecutionMapper;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

public class StepExecutionMapperDecorator implements StepExecutionMapper {

    @Autowired
    @Qualifier("delegate")
    private StepExecutionMapper delegate;

    @Override
    public StepExecutionDTO toDTO(StepExecution stepExecution) {
        var dto = delegate.toDTO(stepExecution);
        if (dto != null && dto.getEndTime() != null && dto.getStartTime() != null) {
            dto.setDurationInMs(Objects.requireNonNull(stepExecution.getEndTime()).getTime() -
                    Objects.requireNonNull(stepExecution.getStartTime()).getTime());
        }
        return dto;
    }

    @Override
    public List<StepExecutionDTO> toDTOs(List<StepExecution> stepExecutions) {
        List<StepExecutionDTO> dtos = null;
        if (!CollectionUtils.isEmpty(stepExecutions)) {
            dtos = stepExecutions.stream().map(this::toDTO).toList();
        }
        return dtos;
    }
}
