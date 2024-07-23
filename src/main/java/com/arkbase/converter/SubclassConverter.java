package com.arkbase.converter;

import com.arkbase.operator.Operator;
import jakarta.persistence.Converter;

@Converter
public class SubclassConverter extends CustomEnumConverter<Operator.Subclass, String> {

  public SubclassConverter() {
    super(Operator.Subclass.class);
  }

  /** {@inheritDoc} */
  @Override
  public String convertToDatabaseColumn(Operator.Subclass enumConstant) {
    return enumConstant.getSubclass();
  }
}
