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
import com.arkbase.operator.OperatorDetailsDTO;
import com.arkbase.operator.OperatorDetailsUpdate;
import com.arkbase.operator.enums.Archetype;
import com.arkbase.operator.enums.Subclass;
import com.arkbase.operator.enums.Trait;
import com.arkbase.skill.NewSkillDTO;
import com.arkbase.skill.Skill;
import com.arkbase.skill.SkillDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomMapper {

  CustomMapper INSTANCE = Mappers.getMapper(CustomMapper.class);

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

  Operator toOperator(NewOperatorDTO newOperator);

  OperatorAttributes toOperatorAttributes(OperatorAttributesDTO attributes);

  @Mapping(target = "id", source = "operator.id")
  OperatorDTO toOperatorDto(Operator operator, OperatorAttributes attributes);

  OperatorAttributesDTO toOperatorAttributesDto(OperatorAttributes attributes);

  Skill toSkill(NewSkillDTO newSkill);

  SkillDTO toSkillDto(Skill skill);

  Material toMaterial(NewMaterialDTO newMaterial);

  MaterialDTO toMaterialDto(Material material);

  void updateOperatorFromDto(
      OperatorDetailsUpdate operatorDetails, @MappingTarget Operator operator);

  @Mapping(target = "id", source = "operator.id")
  OperatorDetailsDTO toOperatorDetailsDto(Operator operator, OperatorAttributes attributes);
}
