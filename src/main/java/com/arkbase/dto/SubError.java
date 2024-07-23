package com.arkbase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubError {
  private String object;
  private String field;
  private Object rejectedValue;
  private String message;
}
