package com.project.yozmcafe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.project.yozmcafe.exception.BadRequestException;
import com.project.yozmcafe.exception.ErrorResponse;
import com.project.yozmcafe.exception.InternalServerException;
import com.project.yozmcafe.exception.TokenException;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final Logger logger = LoggerFactory.getLogger("Exception Handler");

    @ExceptionHandler
    public ResponseEntity<String> handle(final Exception e) {
        logger.error("Internal Error occurred", e);
        return ResponseEntity.internalServerError().body("서버 내부 에러 발생");
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(final BadRequestException e) {
        logger.info("Bad Request: {}", e.getErrorResponse());
        return ResponseEntity.badRequest().body(e.getErrorResponse());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(final InternalServerException e) {
        logger.error("Internal Server Error: {}", e.getErrorResponse());
        return ResponseEntity.internalServerError().body(e.getErrorResponse());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handle(final TokenException e) {
        logger.info("Invalid Token: {}", e.getErrorResponse());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getErrorResponse());
    }
}
