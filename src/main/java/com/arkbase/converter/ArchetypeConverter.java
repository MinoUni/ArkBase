package com.arkbase.converter;

import com.arkbase.operator.Operator;
import jakarta.persistence.Converter;

@Converter
public class ArchetypeConverter extends CustomEnumConverter<Operator.Archetype, String> {

  public ArchetypeConverter() {
    super(Operator.Archetype.class);
  }

  /** {@inheritDoc} */
  @Override
  public String convertToDatabaseColumn(Operator.Archetype enumConstant) {
    return enumConstant.getArchetype();
  }
}
