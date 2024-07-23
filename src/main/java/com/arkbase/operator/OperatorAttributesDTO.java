package com.arkbase.operator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperatorAttributesDTO {

  private Integer hp;

  private Integer atk;

  private Integer def;

  private Integer res;

  private Integer block;

  private Integer deploymentCost;

  private String redeploymentTime;

  private String aspd;
}
