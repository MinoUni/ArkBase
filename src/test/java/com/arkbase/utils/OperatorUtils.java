package com.arkbase.utils;

import com.arkbase.attribute.OperatorAttributes;
import com.arkbase.enums.Rarity;
import com.arkbase.operator.Operator;
import com.arkbase.operator.OperatorAttributesDTO;
import com.arkbase.operator.OperatorCreationDTO;
import com.arkbase.operator.OperatorDTO;

public class OperatorUtils {

  public static OperatorCreationDTO buildOperatorCreationDto() {
    return OperatorCreationDTO.builder()
        .codeName("Ray")
        .archetype(Operator.Archetype.SNIPER.getArchetype())
        .subclass(Operator.Subclass.HUNTER.getSubclass())
        .trait(Operator.Trait.HUNTER.name())
        .rarity(Rarity.SIX_STAR.name())
        .position(Operator.Position.RANGED.name())
        .attackType(Operator.AttackType.PHYSICAL_DAMAGE.name())
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
        .archetype(Operator.Archetype.SNIPER)
        .subclass(Operator.Subclass.HUNTER)
        .trait(Operator.Trait.HUNTER)
        .rarity(Rarity.SIX_STAR)
        .position(Operator.Position.RANGED)
        .attackType(Operator.AttackType.PHYSICAL_DAMAGE)
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
        Operator.Archetype.SNIPER.name(),
        Operator.Subclass.HUNTER.name(),
        Rarity.SIX_STAR.name(),
        Operator.Trait.HUNTER.name(),
        Operator.Position.RANGED.name(),
        Operator.AttackType.PHYSICAL_DAMAGE.name());
  }

  public static OperatorAttributes buildOperatorAttributes() {
    return new OperatorAttributes(2000, 1130, 200, 10, 1, 20, "Fast", "Long");
  }
}
