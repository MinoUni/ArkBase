package com.arkbase.material;

import com.arkbase.converter.CustomEnumConverter;
import com.arkbase.enums.Rarity;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
class MaterialRarityConverter extends CustomEnumConverter<Rarity, String> {

  public MaterialRarityConverter() {
    super(Rarity.class);
  }

  /** {@inheritDoc} */
  @Override
  public String convertToDatabaseColumn(Rarity enumConstant) {
    return enumConstant.getRarity();
  }
}
