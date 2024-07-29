package com.arkbase.utils;

import com.arkbase.attribute.OperatorAttributes;
import com.arkbase.enums.Rarity;
import com.arkbase.material.NewMaterialDTO;
import com.arkbase.operator.NewOperatorDTO;
import com.arkbase.operator.Operator;
import com.arkbase.operator.OperatorAttributesDTO;
import com.arkbase.operator.OperatorDTO;
import com.arkbase.operator.enums.Archetype;
import com.arkbase.operator.enums.AttackType;
import com.arkbase.operator.enums.Position;
import com.arkbase.operator.enums.Subclass;
import com.arkbase.operator.enums.Trait;
import com.arkbase.skill.ActivationType;
import com.arkbase.skill.ChargeType;
import com.arkbase.skill.NewSkillDTO;
import com.arkbase.skill.Skill;
import com.arkbase.skill.SkillDTO;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class TestUtils {

  public static NewOperatorDTO buildNewOperatorDto() {
    return NewOperatorDTO.builder()
        .codeName("Ray")
        .archetype(Archetype.SNIPER.name())
        .subclass(Subclass.HUNTER.name())
        .trait(Trait.HUNTER.name())
        .rarity(Rarity.SIX_STAR.name())
        .position(Position.RANGED.name())
        .attackType(AttackType.PHYSICAL_DAMAGE.name())
        .attributes(buildOperatorAttributesDto())
        .skills(
            Set.of(
                buildOperatorNewSkillDto("SKill-1"),
                buildOperatorNewSkillDto("SKill-2"),
                buildOperatorNewSkillDto("SKill-3")))
        .build();
  }

  public static NewSkillDTO buildOperatorNewSkillDto(String name) {
    return NewSkillDTO.builder()
        .name(name)
        .effect("TEXT")
        .level(7)
        .mastery(3)
        .spCost(20)
        .duration(10)
        .activationType(ActivationType.MANUAL_TRIGGER)
        .chargeType(ChargeType.PASSIVE)
        .build();
  }

  public static Skill buildSkill(String name) {
    return Skill.builder().name(name).build();
  }

  public static NewMaterialDTO buildNewMaterialDto(String name) {
    return NewMaterialDTO.builder()
        .name(name)
        .rarity(Rarity.FIVE_STAR)
        .description("TEXT")
        .quantity(5)
        .build();
  }

  public static OperatorAttributesDTO buildOperatorAttributesDto() {
    return OperatorAttributesDTO.builder()
        .hp(2000)
        .atk(1130)
        .def(200)
        .res(10)
        .block(1)
        .aspd("Fast")
        .deployCost(20)
        .redeployTime("Long")
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
        .attributes(buildOperatorAttributesDto())
        .skills(List.of(buildSkillDto("Skill-1"), buildSkillDto("Skill-2")))
        .build();
  }

  public static SkillDTO buildSkillDto(String name) {
    return SkillDTO.builder()
        .name(name)
        .effect("TEXT")
        .level(7)
        .mastery(3)
        .spCost(20)
        .spInitial(17)
        .duration(10)
        .activationType(ActivationType.MANUAL_TRIGGER)
        .chargeType(ChargeType.PASSIVE)
        .build();
  }

  public static Operator buildOperator() {
    return Operator.builder()
        .codeName("Ray")
        .archetype(Archetype.SNIPER)
        .subclass(Subclass.HUNTER)
        .rarity(Rarity.SIX_STAR)
        .trait(Trait.HUNTER)
        .position(Position.RANGED)
        .attackType(AttackType.PHYSICAL_DAMAGE)
        .attributes(buildOperatorAttributes())
        .build();
  }

  public static OperatorAttributes buildOperatorAttributes() {
    return OperatorAttributes.builder()
        .hp(2000)
        .atk(1130)
        .def(200)
        .res(10)
        .block(1)
        .deployCost(20)
        .redeployTime("Long")
        .aspd("Fast")
        .build();
  }

  public static String readWholeFile(String fileName) throws Exception {
    String filePath = "/test-json-samples/" + fileName;
    try (InputStream inputStream = TestUtils.class.getResourceAsStream(filePath)) {
      return new String(Objects.requireNonNull(inputStream).readAllBytes());
    }
  }
}
