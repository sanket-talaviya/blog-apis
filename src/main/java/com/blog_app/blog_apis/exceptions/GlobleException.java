package com.blog_app.blog_apis.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog_app.blog_apis.payloads.ApiResponse;

@RestControllerAdvice
public class GlobleException {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiResponse> resourceNotFoundException(
    ResourceNotFoundException ex
  ) {
    String messege = ex.getMessage();
    ApiResponse apiResponse = new ApiResponse(messege, false);
    return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(
    MethodArgumentNotValidException ex
  ) {
    Map<String, String> errors = new HashMap<>();
    ex
      .getBindingResult()
      .getAllErrors()
      .forEach(error -> {
        String fieldName = ((FieldError) error).getField();
        String messege = error.getDefaultMessage();
        errors.put(fieldName, messege);
      });
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }
}
