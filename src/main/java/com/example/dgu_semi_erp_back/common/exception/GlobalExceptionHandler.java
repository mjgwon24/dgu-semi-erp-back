package com.example.dgu_semi_erp_back.common.exception;

import com.example.dgu_semi_erp_back.common.exception.response.ApiErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 내장된 AOP (다른 라이브러리 없이 사용 가능한 AOP)
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<?> handleCustomException(CustomException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        var responseBody = ApiErrorResponse.builder()
                .status(errorCode.getStatus().value())
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity
                .status(exception.errorCode.getStatus())
                .body(responseBody);

    }
}