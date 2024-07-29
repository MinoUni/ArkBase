package com.arkbase.operator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperatorAttributesDTO {

  @NotNull @PositiveOrZero private Integer hp;

  @NotNull @PositiveOrZero private Integer atk;

  @NotNull @PositiveOrZero private Integer def;

  @NotNull @PositiveOrZero private Integer res;

  @NotNull @PositiveOrZero private Integer block;

  @NotNull @PositiveOrZero private Integer deploymentCost;

  @NotBlank private String redeploymentTime;

  @NotBlank private String aspd;
}
