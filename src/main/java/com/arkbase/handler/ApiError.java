package com.arkbase.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiError {

  private HttpStatus status;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;

  private String message;

  private List<SubError> subErrors;

  private ApiError() {
    timestamp = LocalDateTime.now();
    subErrors = new ArrayList<>();
  }

  public ApiError(HttpStatus status, String message) {
    this();
    this.status = status;
    this.message = message;
  }
}
