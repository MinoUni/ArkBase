package com.arkbase.operator;

import com.arkbase.enums.Rarity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperatorDTO {

  private Integer id;
  private String codeName;
  private Operator.Archetype archetype;
  private Operator.Subclass subclass;
  private Operator.Trait trait;
  private Rarity rarity;
  private Operator.Position position;
  private Operator.AttackType attackType;
  private OperatorAttributesDTO attributes;
}
