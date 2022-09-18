package com.wd.bym.webapp.exception;

import com.wd.bym.webapp.exception.tools.ApplicationException;
import com.wd.bym.webapp.exception.tools.ExceptionType;

import java.io.Serial;

public class NotFoundException extends ApplicationException {

    @Serial
    private static final long serialVersionUID = -659255425388174159L;

    private static final String NOT_FOUND_EXCEPTION_PREFIX = "error.server.not-found.";

    public enum NotFoundExceptionType implements ExceptionType {
        ENTITY_NOT_FOUND(NOT_FOUND_EXCEPTION_PREFIX + "entity.title",
                NOT_FOUND_EXCEPTION_PREFIX + "entity.msg",
                NOT_FOUND_EXCEPTION_PREFIX + "entity.cause"),

        BATCH_NOT_FOUND(NOT_FOUND_EXCEPTION_PREFIX + "batch.title",
                NOT_FOUND_EXCEPTION_PREFIX + "batch.msg",
                NOT_FOUND_EXCEPTION_PREFIX + "batch.cause");

        private final String messageKey;
        private final String titleKey;
        private final String messageCause;

        NotFoundExceptionType(String titleKey, String messageKey, String messageCause) {
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

    public NotFoundException(NotFoundExceptionType type) {
        super(type);
    }

    public NotFoundException(NotFoundExceptionType type, Object[] valueParams, Object... keyParams) {
        super(type, valueParams, keyParams);
    }

    public NotFoundException(NotFoundExceptionType type, Throwable cause, Object[] valueParams, Object... keyParams) {
        super(type, cause, valueParams, keyParams);
    }

    public NotFoundException(NotFoundExceptionType type, Throwable cause) {
        super(type, cause);
    }

    public NotFoundException(NotFoundExceptionType type, String message, Throwable cause, Object... keyParams) {
        super(type, message, cause, keyParams);
    }

    public NotFoundException(NotFoundExceptionType type, Throwable cause, Object... valueParams) {
        super(type, cause, valueParams);
    }

    public NotFoundException(NotFoundExceptionType type, String message) {
        super(type, message);
    }
}
