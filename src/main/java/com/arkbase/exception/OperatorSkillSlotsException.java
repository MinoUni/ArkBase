package com.arkbase.exception;

public class OperatorSkillSlotsException extends RuntimeException {

  public OperatorSkillSlotsException(String operatorCodename) {
    super(String.format("Operator {%s} has no free skill slots left.", operatorCodename));
  }
}
