package com.arkbase.operator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Archetype {
  VANGUARD("Vanguard"),
  GUARD("Guard"),
  DEFENDER("Defender"),
  SNIPER("Sniper"),
  CASTER("Caster"),
  MEDIC("Medic"),
  SUPPORTER("Supporter"),
  SPECIALIST("Specialist");

  private final String archetype;
}
