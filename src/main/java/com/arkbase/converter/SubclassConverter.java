package com.arkbase.converter;

import com.arkbase.operator.Subclass;
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
