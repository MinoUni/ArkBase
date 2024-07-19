package com.arkbase.converter;

import com.arkbase.operator.Archetype;
import jakarta.persistence.Converter;

@Converter
public class ArchetypeConverter extends CustomEnumConverter<Archetype, String> {

  public ArchetypeConverter() {
    super(Archetype.class);
  }

  /** {@inheritDoc} */
  @Override
  public String convertToDatabaseColumn(Archetype enumConstant) {
    return enumConstant.getArchetype();
  }
}
