package com.arkbase.handler;

import com.arkbase.exception.OperatorAlreadyExistsException;
import com.arkbase.exception.OperatorNotFoundException;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      @NonNull MethodArgumentNotValidException e,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status,
      @NonNull WebRequest request) {
    ApiError apiError = new ApiError(HttpStatus.valueOf(status.value()), "Validation failed");
    List<SubError> subErrors = apiError.getSubErrors();
    e.getBindingResult()
        .getFieldErrors()
        .forEach(
            error -> {
              SubError subError =
                  new SubError(
                      error.getObjectName(),
                      error.getField(),
                      error.getRejectedValue(),
                      error.getDefaultMessage());
              subErrors.add(subError);
            });
    return ResponseEntity.status(status)
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(apiError);
  }

  @ExceptionHandler(OperatorAlreadyExistsException.class)
  public ResponseEntity<Object> handleOperatorAlreadyExistsException(
      OperatorAlreadyExistsException e) {
    HttpStatus status = HttpStatus.CONFLICT;
    ApiError apiError = new ApiError(status, e.getMessage());
    return ResponseEntity.status(status)
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(apiError);
  }

  @ExceptionHandler(OperatorNotFoundException.class)
  public ResponseEntity<Object> handleOperatorNotFoundException(OperatorNotFoundException e) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    ApiError apiError = new ApiError(status, e.getMessage());
    return ResponseEntity.status(status)
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(apiError);
  }
}
