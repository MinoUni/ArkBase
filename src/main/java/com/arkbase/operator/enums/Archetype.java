package com.arkbase.operator.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
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
