package com.arkbase.operator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.arkbase.enums.Rarity;
import com.arkbase.exception.OperatorSkillSlotsException;
import com.arkbase.exception.SkillAlreadySlottedException;
import com.arkbase.skill.Skill;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class OperatorTest {

  @Test
  void shouldAddSkill() {

    Operator operator =
        Operator.builder()
            .id(1)
            .rarity(Rarity.SIX_STAR)
            .skills(
                new HashSet<>(
                    Set.of(
                        Skill.builder().name("Earthquake").build(),
                        Skill.builder().name("Camouflage").build())))
            .build();
    Skill skill = Skill.builder().name("Attack Boost").build();

    assertDoesNotThrow(() -> operator.addSkill(skill));

    assertEquals(3, operator.getSkills().size());
    assertTrue(operator.getSkills().contains(skill));
  }

  @Test
  void shouldThrowSkillAlreadySlottedExceptionWhenAddAlreadySlottedSkill() {
    int operatorId = 1;
    String opCodename = "Ray";
    String skillName = "Attack Boost";
    String exceptionMessage =
        String.format("Operator {%s} already slotted skill {%s}.", opCodename, skillName);
    Skill skill = Skill.builder().name(skillName).build();
    Operator operator =
        Operator.builder()
            .id(operatorId)
            .codeName(opCodename)
            .rarity(Rarity.SIX_STAR)
            .skills(Set.of(skill, Skill.builder().name("Camouflage").build()))
            .build();

    var e = assertThrows(SkillAlreadySlottedException.class, () -> operator.addSkill(skill));

    assertEquals(2, operator.getSkills().size());
    assertEquals(exceptionMessage, e.getMessage());
  }

  @Test
  void shouldThrowOperatorSkillSlotsExceptionWhenAddSkillToOperatorWithNoFreeSkillSlots() {
    int operatorId = 1;
    String opCodename = "Ray";
    Operator operator =
        Operator.builder()
            .id(operatorId)
            .codeName(opCodename)
            .rarity(Rarity.FIVE_STAR)
            .skills(
                Set.of(
                    Skill.builder().name("Earthquake").build(),
                    Skill.builder().name("Camouflage").build()))
            .build();
    Skill skill = Skill.builder().name("Attack Boost").build();
    String exceptionMessage =
        String.format("Operator {%s} has no free skill slots left.", opCodename);

    var e = assertThrows(OperatorSkillSlotsException.class, () -> operator.addSkill(skill));

    assertEquals(exceptionMessage, e.getMessage());
    assertTrue(operator.getSkills().size() != 3);
    assertFalse(operator.getSkills().contains(skill));
  }

  @Test
  void removeSkill() {
    Skill skill = Skill.builder().name("Attack Boost").build();
    Operator operator =
        Operator.builder()
            .id(1)
            .rarity(Rarity.FIVE_STAR)
            .skills(new HashSet<>(Set.of(skill, Skill.builder().name("Camouflage").build())))
            .build();

    assertDoesNotThrow(() -> operator.removeSkill(skill));

    assertEquals(1, operator.getSkills().size());
    assertFalse(operator.getSkills().contains(skill));
  }
}
