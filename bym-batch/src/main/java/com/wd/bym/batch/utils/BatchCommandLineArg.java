package com.wd.bym.batch.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BatchCommandLineArg {

    BATCH_NAME("batch.name");

    private final String argName;

}
