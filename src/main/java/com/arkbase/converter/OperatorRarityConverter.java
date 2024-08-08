package com.arkbase.converter;

import com.arkbase.operator.Rarity;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OperatorRarityConverter extends CustomEnumConverter<Rarity, String> {

  public OperatorRarityConverter() {
    super(Rarity.class);
  }

  /** {@inheritDoc} */
  @Override
  public String convertToDatabaseColumn(Rarity enumConstant) {
    return enumConstant.getRarity();
  }
}
