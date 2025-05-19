package com.latelier.tenisuapi.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoPlayerFoundException.class)
    public ResponseEntity<ProblemDetail> handleNoPlayerFound(NoPlayerFoundException ex) {
        var detail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        detail.setDetail(ex.getMessage());
        return ResponseEntity.status(detail.getStatus()).body(detail);
    }
}