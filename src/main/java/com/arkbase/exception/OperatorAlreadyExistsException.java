package com.arkbase.exception;

public class OperatorAlreadyExistsException extends RuntimeException {

  public OperatorAlreadyExistsException(String codeName) {
    super(String.format("Operator {%s} already exists!", codeName));
  }
}
