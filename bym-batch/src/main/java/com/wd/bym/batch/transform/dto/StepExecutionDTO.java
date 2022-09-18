package com.wd.bym.batch.transform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class StepExecutionDTO {

    private String stepName;

    private String status;

    private int readCount;

    private int writeCount;

    private int commitCount;

    private String error;

    private String exitCode;

    private long durationInMs;

    private ZonedDateTime startTime;

    private ZonedDateTime endTime;
}
