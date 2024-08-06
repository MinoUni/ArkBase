package com.arkbase.exception;

public class SkillAlreadySlottedException extends RuntimeException {

  public SkillAlreadySlottedException(String operatorCodename, String skillName) {
    super(String.format("Operator {%s} already slotted skill {%s}.", operatorCodename, skillName));
  }
}
