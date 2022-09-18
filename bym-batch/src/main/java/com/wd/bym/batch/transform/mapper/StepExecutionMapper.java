package com.wd.bym.batch.transform.mapper;

import com.wd.bym.batch.transform.dto.StepExecutionDTO;
import com.wd.bym.batch.transform.mapper.decorators.StepExecutionMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.batch.core.StepExecution;

import java.util.List;

@DecoratedWith(StepExecutionMapperDecorator.class)
@Mapper(componentModel = "spring")
public interface StepExecutionMapper {

    @Mapping(target = "durationInMs", ignore = true)
    @Mapping(source = "exitStatus.exitDescription", target = "error")
    @Mapping(source = "exitStatus.exitCode", target = "exitCode")
    StepExecutionDTO toDTO(StepExecution stepExecution);

    List<StepExecutionDTO> toDTOs(List<StepExecution> stepExecutions);
}
