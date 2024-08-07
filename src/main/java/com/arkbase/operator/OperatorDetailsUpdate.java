package com.arkbase.operator;

import com.arkbase.operator.enums.Archetype;
import com.arkbase.operator.enums.AttackType;
import com.arkbase.operator.enums.Position;
import com.arkbase.operator.enums.Subclass;
import com.arkbase.operator.enums.Trait;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Builder
public record OperatorDetailsUpdate(
    String codeName,
    Archetype archetype,
    Subclass subclass,
    Rarity rarity,
    Trait trait,
    Position position,
    AttackType attackType,
    @Valid Attributes attributes) {

  @Builder
  public record Attributes(
      @PositiveOrZero Integer hp,
      @PositiveOrZero Integer atk,
      @PositiveOrZero Integer def,
      @PositiveOrZero Integer res,
      @PositiveOrZero Integer block,
      @PositiveOrZero Integer deployCost,
      String redeployTime,
      String aspd) {}
}
