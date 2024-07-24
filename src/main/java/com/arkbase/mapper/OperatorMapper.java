package com.arkbase.mapper;

import com.arkbase.attribute.OperatorAttributes;
import com.arkbase.operator.Operator;
import com.arkbase.operator.OperatorAttributesDTO;
import com.arkbase.operator.OperatorCreationDTO;
import com.arkbase.operator.OperatorDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface OperatorMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "materials", ignore = true)
  @Mapping(target = "skills", ignore = true)
  Operator toOperator(OperatorCreationDTO newOperator);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "operator", ignore = true)
  OperatorAttributes toOperatorAttributes(OperatorAttributesDTO attributes);

  @Mapping(target = "id", source = "operator.id")
  OperatorDTO toOperatorDto(Operator operator, OperatorAttributes attributes);

  OperatorAttributesDTO toOperatorAttributesDto(OperatorAttributes attributes);
}
