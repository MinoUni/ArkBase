package com.arkbase.converter;

import com.arkbase.operator.enums.Subclass;
import jakarta.persistence.Converter;

@Converter
public class SubclassConverter extends CustomEnumConverter<Subclass, String> {

  public SubclassConverter() {
    super(Subclass.class);
  }

  /** {@inheritDoc} */
  @Override
  public String convertToDatabaseColumn(Subclass enumConstant) {
    return enumConstant.getSubclass();
  }
}
