package com.arkbase.material;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Rarity {
  ONE_STAR("1★"),
  TWO_STAR("2★"),
  THREE_STAR("3★"),
  FOUR_STAR("4★"),
  FIVE_STAR("5★"),
  SIX_STAR("6★");

  private final String rarity;
}
