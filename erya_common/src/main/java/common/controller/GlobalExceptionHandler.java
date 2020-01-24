package common.controller;

import common.exception.EryaException;
import common.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result> handlerException(Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            logger.error(stackTraceElement.toString());
        }
        if (e instanceof NoHandlerFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Result(404, e.getMessage()));
        } else if (e instanceof EryaException) {
            return ResponseEntity.status(((EryaException) e).getCode()).body(new Result(((EryaException) e).getCode(), e.getMessage()));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Result(500, e.getMessage()));
        }
    }
}