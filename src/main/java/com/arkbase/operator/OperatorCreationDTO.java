package com.arkbase.operator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperatorCreationDTO {

  private String codeName;

  private String archetype;

  private String subclass;

  private String rarity;

  private String trait;

  private String position;

  private String attackType;

  private OperatorAttributesDTO attributes;
}
