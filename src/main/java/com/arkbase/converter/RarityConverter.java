package com.arkbase.converter;

import com.arkbase.enums.Rarity;
import jakarta.persistence.Converter;

@Converter
public class RarityConverter extends CustomEnumConverter<Rarity, String> {

  public RarityConverter() {
    super(Rarity.class);
  }

  /** {@inheritDoc} */
  @Override
  public String convertToDatabaseColumn(Rarity enumConstant) {
    return enumConstant.getRarity();
  }
}
