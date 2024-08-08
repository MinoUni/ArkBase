package com.arkbase.operator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Rarity {
  ONE_STAR("1★", 0),
  TWO_STAR("2★", 0),
  THREE_STAR("3★", 1),
  FOUR_STAR("4★", 2),
  FIVE_STAR("5★", 2),
  SIX_STAR("6★", 3);

  private final String rarity;
  private final int maxSkillSlots;
}
