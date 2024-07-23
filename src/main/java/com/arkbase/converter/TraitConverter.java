package com.arkbase.converter;

import com.arkbase.operator.Operator;
import jakarta.persistence.Converter;

@Converter
public class TraitConverter extends CustomEnumConverter<Operator.Trait, String> {

  public TraitConverter() {
    super(Operator.Trait.class);
  }

  /** {@inheritDoc} */
  @Override
  public String convertToDatabaseColumn(Operator.Trait enumConstant) {
    return enumConstant.getTrait();
  }
}
