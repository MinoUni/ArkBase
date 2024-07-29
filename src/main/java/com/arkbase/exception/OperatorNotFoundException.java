package com.arkbase.exception;

public class OperatorNotFoundException extends RuntimeException {

  public OperatorNotFoundException(String codeName) {
    super(String.format("Operator with codeName {%s} not found.", codeName));
  }

  public OperatorNotFoundException(Integer id) {
    super(String.format("Operator with id {%d} not found.", id));
  }
}
