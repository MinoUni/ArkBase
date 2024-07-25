package com.arkbase.operator;

import com.arkbase.annotation.ValueOfEnum;
import com.arkbase.enums.Rarity;
import com.arkbase.operator.enums.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperatorCreationDTO {

  @NotBlank private String codeName;

  @ValueOfEnum(enumClass = Archetype.class)
  private String archetype;

  @ValueOfEnum(enumClass = Subclass.class)
  private String subclass;

  @ValueOfEnum(enumClass = Rarity.class)
  private String rarity;

  @ValueOfEnum(enumClass = Trait.class)
  private String trait;

  @ValueOfEnum(enumClass = Position.class)
  private String position;

  @ValueOfEnum(enumClass = AttackType.class)
  private String attackType;

  private OperatorAttributesDTO attributes;
}
