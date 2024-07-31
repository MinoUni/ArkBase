package com.arkbase.mapper;

import com.arkbase.attribute.OperatorAttributes;
import com.arkbase.enums.Rarity;
import com.arkbase.material.Material;
import com.arkbase.material.MaterialDTO;
import com.arkbase.material.NewMaterialDTO;
import com.arkbase.operator.NewOperatorDTO;
import com.arkbase.operator.Operator;
import com.arkbase.operator.OperatorAttributesDTO;
import com.arkbase.operator.OperatorDTO;
import com.arkbase.operator.enums.Archetype;
import com.arkbase.operator.enums.Subclass;
import com.arkbase.operator.enums.Trait;
import com.arkbase.skill.NewSkillDTO;
import com.arkbase.skill.Skill;
import com.arkbase.skill.SkillDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CustomMapper {

  default String archetypeToString(Archetype archetype) {
    return archetype.getArchetype();
  }

  default String subclassToString(Subclass subclass) {
    return subclass.getSubclass();
  }

  default String traitToString(Trait trait) {
    return trait.getTrait();
  }

  default String rarityToString(Rarity rarity) {
    return rarity.getRarity();
  }

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "attributes", ignore = true)
  @Mapping(target = "materials", ignore = true)
  @Mapping(target = "skills", ignore = true)
  Operator toOperator(NewOperatorDTO newOperator);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "operator", ignore = true)
  OperatorAttributes toOperatorAttributes(OperatorAttributesDTO attributes);

  @Mapping(target = "id", source = "operator.id")
  OperatorDTO toOperatorDto(Operator operator, OperatorAttributes attributes);

  OperatorAttributesDTO toOperatorAttributesDto(OperatorAttributes attributes);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "materials", ignore = true)
  @Mapping(target = "operators", ignore = true)
  Skill toSkill(NewSkillDTO newSkill);

  SkillDTO toSkillDto(Skill skill);

  Material toMaterial(NewMaterialDTO newMaterial);

  MaterialDTO toMaterialDto(Material material);
}
