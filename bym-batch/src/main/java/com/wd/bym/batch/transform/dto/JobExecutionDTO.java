package com.wd.bym.batch.transform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class JobExecutionDTO {

    private Long id;

    private String jobName;

    private ZonedDateTime startTime;

    private ZonedDateTime endTime;

    private ZonedDateTime createTime;

    private String error;

    private String exitCode;

    private String status;

    private long durationInMs;

    private List<StepExecutionDTO> stepExecutions;
}
