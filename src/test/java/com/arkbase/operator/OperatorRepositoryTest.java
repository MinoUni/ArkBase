package com.arkbase.operator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.arkbase.enums.Rarity;
import com.arkbase.material.Material;
import com.arkbase.skill.ActivationType;
import com.arkbase.skill.ChargeType;
import com.arkbase.skill.Skill;
import com.arkbase.utils.OperatorUtils;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class OperatorRepositoryTest {

  @Autowired private EntityManager entityManager;

  @Autowired private OperatorRepository repository;

  @Test
  void shouldSaveNewOperator() {
    Operator operator = OperatorUtils.buildOperator();

    Material mat_1 =
        Material.builder().name("MAT-1").rarity(Rarity.FIVE_STAR).description("TEXT").build();
    Material mat_2 =
        Material.builder().name("MAT-2").rarity(Rarity.FIVE_STAR).description("TEXT").build();
    Material mat_3 =
        Material.builder().name("MAT-3").rarity(Rarity.FIVE_STAR).description("TEXT").build();

    operator.addMaterial(mat_1, 5);
    operator.addMaterial(mat_2, 5);

    var skill_1 =
        Skill.builder()
            .name("SKILL-1")
            .description("TEXT")
            .level(7)
            .mastery(3)
            .spCost(20)
            .duration(10)
            .activationType(ActivationType.MANUAL_TRIGGER)
            .chargeType(ChargeType.PASSIVE)
            .build();
    skill_1.addMaterial(mat_1, 5).addMaterial(mat_2, 5);

    var skill_2 =
        Skill.builder()
            .name("SKILL-2")
            .description("TEXT")
            .level(7)
            .mastery(3)
            .spCost(20)
            .duration(10)
            .activationType(ActivationType.MANUAL_TRIGGER)
            .chargeType(ChargeType.PASSIVE)
            .build();
    skill_2.addMaterial(mat_3, 5);

    operator.addSkill(skill_1);
    operator.addSkill(skill_2);

    var savedOperator = repository.save(operator);

    assertEquals(operator, savedOperator);
  }
}
