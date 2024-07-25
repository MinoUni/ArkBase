package com.arkbase.utils;

import com.arkbase.attribute.OperatorAttributes;
import com.arkbase.enums.Rarity;
import com.arkbase.operator.Operator;
import com.arkbase.operator.OperatorAttributesDTO;
import com.arkbase.operator.OperatorCreationDTO;
import com.arkbase.operator.OperatorDTO;
import com.arkbase.operator.enums.Archetype;
import com.arkbase.operator.enums.AttackType;
import com.arkbase.operator.enums.Position;
import com.arkbase.operator.enums.Subclass;
import com.arkbase.operator.enums.Trait;

public class OperatorUtils {

  public static OperatorCreationDTO buildOperatorCreationDto() {
    return OperatorCreationDTO.builder()
        .codeName("Ray")
        .archetype(Archetype.SNIPER.name())
        .subclass(Subclass.HUNTER.name())
        .trait(Trait.HUNTER.name())
        .rarity(Rarity.SIX_STAR.name())
        .position(Position.RANGED.name())
        .attackType(AttackType.PHYSICAL_DAMAGE.name())
        .attributes(
            OperatorAttributesDTO.builder()
                .hp(2000)
                .atk(1130)
                .def(200)
                .res(10)
                .block(1)
                .aspd("Fast")
                .deploymentCost(20)
                .redeploymentTime("Long")
                .build())
        .build();
  }

  public static OperatorDTO buildOperatorDto() {
    return OperatorDTO.builder()
        .id(1)
        .codeName("Ray")
        .archetype(Archetype.SNIPER)
        .subclass(Subclass.HUNTER)
        .trait(Trait.HUNTER)
        .rarity(Rarity.SIX_STAR)
        .position(Position.RANGED)
        .attackType(AttackType.PHYSICAL_DAMAGE)
        .attributes(
            OperatorAttributesDTO.builder()
                .hp(2000)
                .atk(1130)
                .def(200)
                .res(10)
                .block(1)
                .aspd("Fast")
                .deploymentCost(20)
                .redeploymentTime("Long")
                .build())
        .build();
  }

  public static Operator buildOperator() {
    return new Operator(
        "Ray",
        Archetype.SNIPER.name(),
        Subclass.HUNTER.name(),
        Rarity.SIX_STAR.name(),
        Trait.HUNTER.name(),
        Position.RANGED.name(),
        AttackType.PHYSICAL_DAMAGE.name());
  }

  public static OperatorAttributes buildOperatorAttributes() {
    return new OperatorAttributes(2000, 1130, 200, 10, 1, 20, "Fast", "Long");
  }
}
