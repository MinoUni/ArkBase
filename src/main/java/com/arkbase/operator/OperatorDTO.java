package com.arkbase.operator;

import com.arkbase.enums.Rarity;
import com.arkbase.operator.enums.Archetype;
import com.arkbase.operator.enums.AttackType;
import com.arkbase.operator.enums.Position;
import com.arkbase.operator.enums.Subclass;
import com.arkbase.operator.enums.Trait;
import com.arkbase.skill.SkillDTO;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.List;
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

  private List<SkillDTO> skills;
}
