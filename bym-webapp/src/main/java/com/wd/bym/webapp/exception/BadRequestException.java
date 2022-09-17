package com.wd.bym.webapp.exception;

import com.wd.bym.webapp.exception.tools.ApplicationException;
import com.wd.bym.webapp.exception.tools.ExceptionType;

import java.io.Serial;

public class BadRequestException extends ApplicationException {

    @Serial
    private static final long serialVersionUID = -659255425388174159L;

    private static final String BAD_REQUEST_EXCEPTION_PREFIX = "error.server.bad-request.";

    public enum BadRequestExceptionType implements ExceptionType {
        CONSTRAINT_NOT_RESPECTED(
                BAD_REQUEST_EXCEPTION_PREFIX + "constraint-not-respected.title",
                BAD_REQUEST_EXCEPTION_PREFIX + "constraint-not-respected.msg",
                BAD_REQUEST_EXCEPTION_PREFIX + "constraint-not-respected.cause");

        private final String messageKey;
        private final String titleKey;
        private final String messageCause;

        BadRequestExceptionType(String titleKey, String messageKey, String messageCause) {
            this.titleKey = titleKey;
            this.messageKey = messageKey;
            this.messageCause = messageCause;
        }

        @Override
        public String getTitleKey() {
            return this.titleKey;
        }

        @Override
        public String getMessageKey() {
            return this.messageKey;
        }

        @Override
        public String getMessageCause() {
            return this.messageCause;
        }
    }

    public BadRequestException(BadRequestExceptionType type) {
        super(type);
    }

    public BadRequestException(BadRequestExceptionType type, Object[] valueParams, Object... keyParams) {
        super(type, valueParams, keyParams);
    }

    public BadRequestException(BadRequestExceptionType type, Throwable cause, Object[] valueParams, Object... keyParams) {
        super(type, cause, valueParams, keyParams);
    }

    public BadRequestException(BadRequestExceptionType type, Throwable cause) {
        super(type, cause);
    }

    public BadRequestException(BadRequestExceptionType type, String message, Throwable cause, Object... keyParams) {
        super(type, message, cause, keyParams);
    }

    public BadRequestException(BadRequestExceptionType type, Throwable cause, Object... valueParams) {
        super(type, cause, valueParams);
    }

    public BadRequestException(BadRequestExceptionType type, String message) {
        super(type, message);
    }
}
