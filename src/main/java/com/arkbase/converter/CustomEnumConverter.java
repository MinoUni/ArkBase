package com.arkbase.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.HashMap;
import java.util.Map;

@Converter
public abstract class CustomEnumConverter<X, Y> implements AttributeConverter<X, Y> {

  private final Map<Y, X> enumMap = new HashMap<>();

  public CustomEnumConverter(Class<X> enumType) {
    X[] enumConstants = enumType.getEnumConstants();
    for (X constant : enumConstants) {
      Y constantValue = convertToDatabaseColumn(constant);
      enumMap.put(constantValue, constant);
    }
  }

  /** {@inheritDoc} */
  @Override
  public X convertToEntityAttribute(Y constantValue) {
    return enumMap.get(constantValue);
  }
}
