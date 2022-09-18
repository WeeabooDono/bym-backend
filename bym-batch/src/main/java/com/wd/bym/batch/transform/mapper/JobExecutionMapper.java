package com.wd.bym.batch.transform.mapper;

import com.wd.bym.batch.transform.dto.JobExecutionDTO;
import com.wd.bym.batch.transform.mapper.decorators.JobExecutionMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.batch.core.JobExecution;

import java.util.List;

@DecoratedWith(JobExecutionMapperDecorator.class)
@Mapper(componentModel = "spring", uses = StepExecutionMapper.class)
public interface JobExecutionMapper {

    @Mapping(target = "durationInMs", ignore = true)
    @Mapping(source = "jobInstance.jobName", target = "jobName")
    @Mapping(source = "exitStatus.exitDescription", target = "error")
    @Mapping(source = "exitStatus.exitCode", target = "exitCode")
    JobExecutionDTO toDTO(JobExecution jobExecution);

    List<JobExecutionDTO> toDTOs(List<JobExecution> jobExecutions);
}
