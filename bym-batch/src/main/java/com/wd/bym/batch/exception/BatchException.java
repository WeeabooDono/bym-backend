package com.wd.bym.batch.exception;

public class BatchException extends Exception {

    public BatchException(String msg) {
        super(msg);
    }

    public BatchException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
