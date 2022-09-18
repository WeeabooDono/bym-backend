package com.wd.bym.webapp.exception;

import com.wd.bym.webapp.exception.tools.ApplicationException;
import com.wd.bym.webapp.exception.tools.ExceptionType;

import java.io.Serial;

public class TechnicalException extends ApplicationException {

    @Serial
    private static final long serialVersionUID = -659255425388174159L;

    private static final String TECHNICAL_EXCEPTION_PREFIX = "error.server.technical.";

    public enum TechnicalExceptionType implements ExceptionType {
        BATCH_START_EXECUTION(TECHNICAL_EXCEPTION_PREFIX + "batch.start.execution.title",
                TECHNICAL_EXCEPTION_PREFIX + "batch.start.execution.msg",
                TECHNICAL_EXCEPTION_PREFIX + "batch.start.execution.cause");

        private final String messageKey;
        private final String titleKey;
        private final String messageCause;

        TechnicalExceptionType(String titleKey, String messageKey, String messageCause) {
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

    public TechnicalException(TechnicalExceptionType type) {
        super(type);
    }

    public TechnicalException(TechnicalExceptionType type, Object[] valueParams, Object... keyParams) {
        super(type, valueParams, keyParams);
    }

    public TechnicalException(TechnicalExceptionType type, Throwable cause, Object[] valueParams, Object... keyParams) {
        super(type, cause, valueParams, keyParams);
    }

    public TechnicalException(TechnicalExceptionType type, Throwable cause) {
        super(type, cause);
    }

    public TechnicalException(TechnicalExceptionType type, String message, Throwable cause, Object... keyParams) {
        super(type, message, cause, keyParams);
    }

    public TechnicalException(TechnicalExceptionType type, Throwable cause, Object... valueParams) {
        super(type, cause, valueParams);
    }

    public TechnicalException(TechnicalExceptionType type, String message) {
        super(type, message);
    }
}
