package com.wd.bym.batch.utils;

import com.wd.bym.batch.exception.NoSuchBatchException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum BatchType {
    MML_MANGA_RETRIEVER_JOB("mmlMangaRetrieverJob");

    private final String jobName;

    public static BatchType getByJobName(String jobName) throws NoSuchBatchException {
        var batchFound = Arrays.stream(BatchType.values())
                .filter(batchType -> batchType.getJobName().equals(jobName))
                .findAny()
                .orElse(null);

        if (batchFound == null) {
            String enumValues = Stream.of(BatchType.values()).map(BatchType::getJobName).collect(Collectors.joining(","));
            throw new NoSuchBatchException(MessageFormat.format("Unknown batch name : {0} - Authorized values are: [{1}]", jobName, enumValues));
        }
        return batchFound;
    }
}
