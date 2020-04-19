package com.learnnbuild.wynkfollow.exception;

import com.learnnbuild.wynkfollow.model.response.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRunTimeException(Exception ex, WebRequest request) {
        LOGGER.error(ex.getMessage());
        ExceptionResponse exceptionBody = new ExceptionResponse("failed", "invalid input parameter");
        return new ResponseEntity<>(exceptionBody, HttpStatus.NOT_FOUND);
    }
}
