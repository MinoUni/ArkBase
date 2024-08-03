package com.arkbase.exception;

public class OperatorSkillSlotsException extends RuntimeException {

  public OperatorSkillSlotsException(Integer operatorId) {
    super(String.format("Operator {%d} has no free skill slots left.", operatorId));
  }
}
