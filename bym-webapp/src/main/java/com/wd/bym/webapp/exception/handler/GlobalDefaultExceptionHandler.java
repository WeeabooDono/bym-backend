package com.wd.bym.webapp.exception.handler;

import com.wd.bym.webapp.exception.BadRequestException;
import com.wd.bym.webapp.exception.NotFoundException;
import com.wd.bym.webapp.exception.tools.ExceptionResponse;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.wd.bym.webapp.exception.BadRequestException.BadRequestExceptionType.CONSTRAINT_NOT_RESPECTED;

@ControllerAdvice
public class GlobalDefaultExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    /**
     * Handle all BadRequestException
     *
     * @param ex      a BadRequestException
     * @param request the web request
     * @return an exception response that contains the detail
     * of the BadRequestException
     */
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(final BadRequestException ex, final WebRequest request) {
        LOG.error(ex.getMessage(), ex);
        return this.handleExceptionInternal(ex,
                new ExceptionResponse(ex.getTitleKey(), ex.getMessageKey(), ex),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Handle all NotFoundException
     *
     * @param ex      a NotFoundException
     * @param request the web request
     * @return an exception response that contains the detail
     * of the NotFoundException
     */
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(final NotFoundException ex, final WebRequest request) {
        LOG.error(ex.getMessage(), ex);
        return this.handleExceptionInternal(ex,
                new ExceptionResponse(ex.getTitleKey(), ex.getMessageKey(), ex),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * Handle a ConstraintViolationException for @Validation
     *
     * @param ex      a ConstraintViolationException
     * @param request the web request
     * @return an exception response that contains the detail
     * of the ConstraintViolationException
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException ex, final WebRequest request) {
        BadRequestException badRequestException = new BadRequestException(
                CONSTRAINT_NOT_RESPECTED,
                ex.getMessage(),
                ex);

        LOG.error(badRequestException.getMessage(), badRequestException);

        return this.handleExceptionInternal(ex,
                new ExceptionResponse(badRequestException.getTitleKey(), badRequestException.getMessage(), badRequestException),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }
}
