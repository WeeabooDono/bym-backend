package com.wd.bym.batch.exception;

public class BatchRunException extends Exception {

    public BatchRunException(String msg) {
        super(msg);
    }

    public BatchRunException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
