package com.arkbase.converter;

import com.arkbase.operator.enums.Trait;
import jakarta.persistence.Converter;

@Converter
public class TraitConverter extends CustomEnumConverter<Trait, String> {

  public TraitConverter() {
    super(Trait.class);
  }

  /** {@inheritDoc} */
  @Override
  public String convertToDatabaseColumn(Trait enumConstant) {
    return enumConstant.getTrait();
  }
}
