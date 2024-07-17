package com.arkbase.converter;

import com.arkbase.enums.Rarity;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter
public class RarityConverter implements AttributeConverter<Rarity, String> {

  @Override
  public Rarity convertToEntityAttribute(String dbData) {
    return Stream.of(Rarity.values())
        .filter(e -> e.getRarity().equals(dbData))
        .findFirst()
        .orElseThrow(() -> new EnumConstantNotPresentException(Rarity.class, dbData));
  }

  @Override
  public String convertToDatabaseColumn(Rarity enumValue) {
    return enumValue.getRarity();
  }
}
