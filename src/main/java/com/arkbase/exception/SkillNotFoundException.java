package com.arkbase.exception;

public class SkillNotFoundException extends RuntimeException {

  public SkillNotFoundException(int skillId) {
    super(String.format("Skill {%d} not found.", skillId));
  }
}
