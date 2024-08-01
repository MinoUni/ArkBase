package com.arkbase.exception;

public class SkillAlreadySlottedException extends RuntimeException {

  public SkillAlreadySlottedException(Integer operatorId, String skillName) {
    super(String.format("Operator {%d} already slotted skill {%s}.", operatorId, skillName));
  }
}
