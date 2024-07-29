package com.arkbase.skill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillDTO {

  private Integer id;

  private String name;

  private String description;

  private Integer spCost;

  private Integer level;

  private Integer mastery;

  private ChargeType chargeType;

  private ActivationType activationType;

  private Integer duration;
}
