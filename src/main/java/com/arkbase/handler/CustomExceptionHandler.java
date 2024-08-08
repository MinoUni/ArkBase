package com.arkbase.handler;

import com.arkbase.exception.OperatorAlreadyExistsException;
import com.arkbase.exception.OperatorNotFoundException;
import com.arkbase.exception.OperatorRarityException;
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
    HttpStatus httpStatus = HttpStatus.valueOf(status.value());
    ApiError apiError = new ApiError(httpStatus, "Validation failed");
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
    return buildResponseEntity(httpStatus, apiError);
  }

  @ExceptionHandler(OperatorAlreadyExistsException.class)
  public ResponseEntity<Object> handleOperatorAlreadyExistsException(
      OperatorAlreadyExistsException e) {
    HttpStatus status = HttpStatus.CONFLICT;
    ApiError apiError = new ApiError(status, e.getMessage());
    return buildResponseEntity(status, apiError);
  }

  @ExceptionHandler(OperatorNotFoundException.class)
  public ResponseEntity<Object> handleOperatorNotFoundException(OperatorNotFoundException e) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    ApiError apiError = new ApiError(status, e.getMessage());
    return buildResponseEntity(status, apiError);
  }

  @ExceptionHandler(OperatorRarityException.class)
  public ResponseEntity<Object> handleOperatorRarityException(OperatorRarityException e) {
    HttpStatus status = HttpStatus.CONFLICT;
    ApiError apiError = new ApiError(status, e.getMessage());
    return buildResponseEntity(status, apiError);
  }

  private ResponseEntity<Object> buildResponseEntity(HttpStatus status, ApiError content) {
    return ResponseEntity.status(status)
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(content);
  }
}
