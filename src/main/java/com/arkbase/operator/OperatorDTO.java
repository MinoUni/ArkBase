package com.arkbase.operator;

import com.arkbase.enums.Rarity;
import com.arkbase.operator.enums.*;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
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

  @JsonUnwrapped
  private Archetype archetype;

  @JsonUnwrapped
  private Subclass subclass;

  @JsonUnwrapped
  private Trait trait;

  @JsonUnwrapped
  private Rarity rarity;

  private Position position;

  private AttackType attackType;

  private OperatorAttributesDTO attributes;
}
