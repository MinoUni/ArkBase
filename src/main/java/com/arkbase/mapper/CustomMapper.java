package com.arkbase.mapper;

import com.arkbase.attribute.OperatorAttributes;
import com.arkbase.material.Material;
import com.arkbase.material.MaterialDTO;
import com.arkbase.material.NewMaterialDTO;
import com.arkbase.operator.NewOperatorDTO;
import com.arkbase.operator.Operator;
import com.arkbase.operator.OperatorAttributesDTO;
import com.arkbase.operator.OperatorDTO;
import com.arkbase.operator.OperatorDetailsDTO;
import com.arkbase.operator.OperatorDetailsUpdate;
import com.arkbase.operator.Rarity;
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
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
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

  @Mapping(target = "skills", ignore = true)
  @Mapping(target = "attributes", ignore = true)
  Operator toOperator(NewOperatorDTO source);

  OperatorAttributes toOperatorAttributes(OperatorAttributesDTO source);

  @Mapping(target = "id", source = "operator.id")
  OperatorDTO toOperatorDto(Operator operator, OperatorAttributes attributes);

  OperatorAttributesDTO toOperatorAttributesDto(OperatorAttributes source);

  Skill toSkill(NewSkillDTO source);

  SkillDTO toSkillDto(Skill source);

  Material toMaterial(NewMaterialDTO source);

  MaterialDTO toMaterialDto(Material source);

  void updateOperatorFromDto(
      OperatorDetailsUpdate source, @MappingTarget Operator target);

  OperatorDetailsDTO toOperatorDetailsDto(Operator source);
}
