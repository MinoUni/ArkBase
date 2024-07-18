package com.arkbase.converter;

import com.arkbase.operator.Subclass;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter
public class SubclassConverter implements AttributeConverter<Subclass, String> {

  @Override
  public String convertToDatabaseColumn(Subclass enumValue) {
    return enumValue.getSubclass();
  }

  @Override
  public Subclass convertToEntityAttribute(String dbData) {
    return Stream.of(Subclass.values())
        .filter(e -> e.getSubclass().equals(dbData))
        .findFirst()
        .orElseThrow(() -> new EnumConstantNotPresentException(Subclass.class, dbData));
  }
}
