package com.arkbase.exception;

import com.arkbase.operator.Rarity;

public class OperatorRarityException extends RuntimeException {

  public OperatorRarityException(Rarity rarity, int skillsCount) {
    super(
        String.format(
            "Rarity {%s} has less skill slots {%d} then operator currently use {%d}",
            rarity.getRarity(), rarity.getMaxSkillSlots(), skillsCount));
  }
}
