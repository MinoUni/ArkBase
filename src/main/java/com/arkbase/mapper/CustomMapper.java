package com.arkbase.mapper;

import com.arkbase.attribute.OperatorAttributes;
import com.arkbase.material.Material;
import com.arkbase.material.MaterialDTO;
import com.arkbase.material.NewMaterialDTO;
import com.arkbase.operator.NewOperatorDTO;
import com.arkbase.operator.Operator;
import com.arkbase.operator.OperatorAttributesDTO;
import com.arkbase.operator.OperatorDTO;
import com.arkbase.skill.NewSkillDTO;
import com.arkbase.skill.Skill;
import com.arkbase.skill.SkillDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CustomMapper {

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

  @Mapping(target = "materials", ignore = true)
  Skill toSkill(NewSkillDTO newSkill);

  SkillDTO toSkillDto(Skill skill);

  Material toMaterial(NewMaterialDTO newMaterial);

  MaterialDTO toMaterialDto(Material material);
}
