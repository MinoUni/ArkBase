package com.arkbase.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.arkbase.attribute.OperatorAttributes;
import com.arkbase.operator.NewOperatorDTO;
import com.arkbase.operator.Operator;
import com.arkbase.operator.OperatorAttributesDTO;
import com.arkbase.operator.OperatorDTO;
import com.arkbase.operator.OperatorDetailsDTO;
import com.arkbase.operator.OperatorDetailsUpdate;
import com.arkbase.operator.Rarity;
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
import java.util.Set;
import org.junit.jupiter.api.Test;

class CustomMapperTest {

  private final CustomMapper mapper = CustomMapper.INSTANCE;

  @Test
  void shouldMapNewOperatorDtoToOperator() {
    NewOperatorDTO source =
        NewOperatorDTO.builder()
            .codeName("Mlynar")
            .rarity(Rarity.SIX_STAR)
            .archetype(Archetype.GUARD)
            .trait(Trait.LIBERATOR)
            .subclass(Subclass.LIBERATOR)
            .attackType(AttackType.PHYSICAL_DAMAGE)
            .position(Position.MELEE)
            .attributes(
                OperatorAttributesDTO.builder()
                    .atk(10)
                    .hp(10)
                    .def(10)
                    .res(10)
                    .block(3)
                    .deployCost(20)
                    .build())
            .skills(
                Set.of(
                    NewSkillDTO.builder().name("Skill-1").level(7).build(),
                    NewSkillDTO.builder().name("Skill-2").level(7).build()))
            .build();

    Operator operator = mapper.toOperator(source);

    assertNotNull(operator);
    assertNull(operator.getAttributes());
    assertEquals(source.codeName(), operator.getCodeName());
    assertEquals(source.rarity(), operator.getRarity());
    assertEquals(source.archetype(), operator.getArchetype());
    assertEquals(source.trait(), operator.getTrait());
    assertEquals(source.subclass(), operator.getSubclass());
    assertEquals(source.attackType(), operator.getAttackType());
    assertEquals(source.position(), operator.getPosition());
    assertEquals(0, operator.getSkills().size());
    assertEquals(0, operator.getMaterials().size());
  }

  @Test
  void shouldMapOperatorDtoFromOperator() {
    OperatorAttributes attributes =
        OperatorAttributes.builder()
            .hp(10)
            .atk(10)
            .def(10)
            .res(10)
            .block(3)
            .deployCost(20)
            .redeployTime("Slow")
            .aspd("Fast")
            .build();
    Operator source =
        Operator.builder()
            .id(1)
            .codeName("GoldenGlow")
            .rarity(Rarity.SIX_STAR)
            .archetype(Archetype.CASTER)
            .subclass(Subclass.DRONE_CASTER)
            .trait(Trait.DRONE_CASTER)
            .position(Position.RANGED)
            .attackType(AttackType.ARTS_DAMAGE)
            .attributes(attributes)
            .skills(
                Set.of(
                    Skill.builder().name("Skill-1").build(),
                    Skill.builder().name("Skill-2").build()))
            .build();

    OperatorDTO result = mapper.toOperatorDto(source);

    assertNotNull(result);
    assertEquals(source.getId(), result.id());
    assertEquals(source.getCodeName(), result.codeName());
    assertEquals(source.getArchetype().getArchetype(), result.archetype());
    assertEquals(source.getSubclass().getSubclass(), result.subclass());
    assertEquals(source.getRarity().getRarity(), result.rarity());
    assertEquals(source.getTrait().getTrait(), result.trait());
    assertEquals(source.getPosition(), result.position());
    assertEquals(source.getAttackType(), result.attackType());
    assertEquals(source.getSkills().size(), result.skills().size());
    assertNotNull(attributes);
    assertEquals(attributes.getHp(), result.attributes().getHp());
    assertEquals(attributes.getAtk(), result.attributes().getAtk());
    assertEquals(attributes.getDef(), result.attributes().getDef());
    assertEquals(attributes.getRes(), result.attributes().getRes());
    assertEquals(attributes.getBlock(), result.attributes().getBlock());
    assertEquals(attributes.getDeployCost(), result.attributes().getDeployCost());
    assertEquals(attributes.getRedeployTime(), result.attributes().getRedeployTime());
    assertEquals(attributes.getAspd(), result.attributes().getAspd());
  }

  @Test
  void shouldUpdateOperatorFromOperatorDetails() {
    OperatorDetailsUpdate source =
        OperatorDetailsUpdate.builder()
            .codeName("Rockrock")
            .archetype(Archetype.GUARD)
            .rarity(Rarity.FOUR_STAR)
            .attributes(
                OperatorDetailsUpdate.Attributes.builder()
                    .hp(100)
                    .atk(100)
                    .def(100)
                    .res(100)
                    .build())
            .build();
    Operator target =
        Operator.builder()
            .id(1)
            .codeName("GoldenGlow")
            .rarity(Rarity.SIX_STAR)
            .archetype(Archetype.CASTER)
            .subclass(Subclass.DRONE_CASTER)
            .trait(Trait.DRONE_CASTER)
            .position(Position.RANGED)
            .attackType(AttackType.ARTS_DAMAGE)
            .attributes(
                OperatorAttributes.builder()
                    .hp(10)
                    .atk(10)
                    .def(10)
                    .res(10)
                    .block(1)
                    .deployCost(20)
                    .redeployTime("Slow")
                    .aspd("Fast")
                    .build())
            .skills(
                Set.of(
                    Skill.builder().name("Skill-1").build(),
                    Skill.builder().name("Skill-2").build()))
            .build();

    mapper.updateOperatorFromDto(source, target);

    assertEquals(source.codeName(), target.getCodeName());
    assertEquals(source.rarity(), target.getRarity());
    assertEquals(source.archetype(), target.getArchetype());
    assertNotEquals(source.subclass(), target.getSubclass());
    assertNotEquals(source.trait(), target.getTrait());
    assertNotEquals(source.attackType(), target.getAttackType());
    assertNotEquals(source.position(), target.getPosition());
    assertEquals(source.attributes().hp(), target.getAttributes().getHp());
    assertEquals(source.attributes().atk(), target.getAttributes().getAtk());
    assertEquals(source.attributes().def(), target.getAttributes().getDef());
    assertEquals(source.attributes().res(), target.getAttributes().getRes());
    assertNotEquals(source.attributes().block(), target.getAttributes().getBlock());
    assertNotEquals(source.attributes().deployCost(), target.getAttributes().getDeployCost());
    assertNotEquals(source.attributes().redeployTime(), target.getAttributes().getRedeployTime());
    assertNotEquals(source.attributes().aspd(), target.getAttributes().getAspd());
  }

  @Test
  void shouldMapOperatorDetailsDtoFromOperator() {
    Operator source =
        Operator.builder()
            .id(1)
            .codeName("GoldenGlow")
            .rarity(Rarity.SIX_STAR)
            .archetype(Archetype.CASTER)
            .subclass(Subclass.DRONE_CASTER)
            .trait(Trait.DRONE_CASTER)
            .position(Position.RANGED)
            .attackType(AttackType.ARTS_DAMAGE)
            .attributes(
                OperatorAttributes.builder()
                    .hp(10)
                    .atk(10)
                    .def(10)
                    .res(10)
                    .block(1)
                    .deployCost(20)
                    .redeployTime("Slow")
                    .aspd("Fast")
                    .build())
            .build();

    OperatorDetailsDTO result = mapper.toOperatorDetailsDto(source);

    assertNotNull(result);
    assertNotNull(result.attributes());
    assertEquals(source.getId(), result.id());
    assertEquals(source.getCodeName(), result.codeName());
    assertEquals(source.getArchetype().getArchetype(), result.archetype());
    assertEquals(source.getSubclass().getSubclass(), result.subclass());
    assertEquals(source.getRarity().getRarity(), result.rarity());
    assertEquals(source.getTrait().getTrait(), result.trait());
    assertEquals(source.getPosition(), result.position());
    assertEquals(source.getAttackType(), result.attackType());
    assertEquals(source.getAttributes().getHp(), result.attributes().getHp());
    assertEquals(source.getAttributes().getAtk(), result.attributes().getAtk());
    assertEquals(source.getAttributes().getDef(), result.attributes().getDef());
    assertEquals(source.getAttributes().getRes(), result.attributes().getRes());
    assertEquals(source.getAttributes().getBlock(), result.attributes().getBlock());
    assertEquals(source.getAttributes().getDeployCost(), result.attributes().getDeployCost());
    assertEquals(source.getAttributes().getRedeployTime(), result.attributes().getRedeployTime());
    assertEquals(source.getAttributes().getAspd(), result.attributes().getAspd());
  }

  @Test
  void shouldMapOperatorAttributesFromOperatorAttributesDto() {
    OperatorAttributesDTO source =
        OperatorAttributesDTO.builder()
            .hp(10)
            .atk(10)
            .def(10)
            .res(10)
            .block(3)
            .deployCost(20)
            .redeployTime("Slow")
            .aspd("Fast")
            .build();

    OperatorAttributes result = mapper.toOperatorAttributes(source);

    assertEquals(source.getHp(), result.getHp());
    assertEquals(source.getAtk(), result.getAtk());
    assertEquals(source.getDef(), result.getDef());
    assertEquals(source.getRes(), result.getRes());
    assertEquals(source.getBlock(), result.getBlock());
    assertEquals(source.getDeployCost(), result.getDeployCost());
    assertEquals(source.getRedeployTime(), result.getRedeployTime());
    assertEquals(source.getAspd(), result.getAspd());
  }

  @Test
  void shouldMapOperatorAttributesToOperatorAttributesDto() {
    OperatorAttributes source =
        OperatorAttributes.builder()
            .hp(10)
            .atk(10)
            .def(10)
            .res(10)
            .block(3)
            .deployCost(20)
            .redeployTime("Slow")
            .aspd("Fast")
            .build();

    OperatorAttributesDTO result = mapper.toOperatorAttributesDto(source);

    assertNotNull(result);
    assertEquals(source.getHp(), result.getHp());
    assertEquals(source.getAtk(), result.getAtk());
    assertEquals(source.getDef(), result.getDef());
    assertEquals(source.getRes(), result.getRes());
    assertEquals(source.getBlock(), result.getBlock());
    assertEquals(source.getDeployCost(), result.getDeployCost());
    assertEquals(source.getRedeployTime(), result.getRedeployTime());
    assertEquals(source.getAspd(), result.getAspd());
  }

  @Test
  void shouldMapSkillFromNewSkillDto() {
    NewSkillDTO source =
        NewSkillDTO.builder()
            .name("Skill-1")
            .effect("Boost attack by 120%")
            .level(7)
            .mastery(7)
            .spCost(40)
            .spInitial(18)
            .duration(15)
            .activationType(ActivationType.MANUAL_TRIGGER)
            .chargeType(ChargeType.PASSIVE)
            .build();

    Skill result = mapper.toSkill(source);

    assertNotNull(result);
    assertEquals(source.getName(), result.getName());
    assertEquals(source.getEffect(), result.getEffect());
    assertEquals(source.getLevel(), result.getLevel());
    assertEquals(source.getMastery(), result.getMastery());
    assertEquals(source.getSpCost(), result.getSpCost());
    assertEquals(source.getSpInitial(), result.getSpInitial());
    assertEquals(source.getDuration(), result.getDuration());
    assertEquals(source.getChargeType(), result.getChargeType());
    assertEquals(source.getActivationType(), result.getActivationType());
  }

  @Test
  void shouldMapSkillToSkillDto() {
    Skill source =
        Skill.builder()
            .name("Skill-1")
            .effect("Boost attack by 120%")
            .level(7)
            .mastery(7)
            .spCost(40)
            .spInitial(18)
            .duration(15)
            .activationType(ActivationType.MANUAL_TRIGGER)
            .chargeType(ChargeType.PASSIVE)
            .build();

    SkillDTO result = mapper.toSkillDto(source);

    assertNotNull(result);
    assertEquals(source.getName(), result.getName());
    assertEquals(source.getEffect(), result.getEffect());
    assertEquals(source.getLevel(), result.getLevel());
    assertEquals(source.getMastery(), result.getMastery());
    assertEquals(source.getSpCost(), result.getSpCost());
    assertEquals(source.getSpInitial(), result.getSpInitial());
    assertEquals(source.getDuration(), result.getDuration());
    assertEquals(source.getChargeType(), result.getChargeType());
    assertEquals(source.getActivationType(), result.getActivationType());
  }
}
