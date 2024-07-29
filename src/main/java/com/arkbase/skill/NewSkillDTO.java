package com.arkbase.skill;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(
    exclude = {
      "effect",
      "spCost",
      "spInitial",
      "level",
      "mastery",
      "chargeType",
      "activationType",
      "duration",
    })
@NoArgsConstructor
@AllArgsConstructor
public class NewSkillDTO {

  @NotBlank
  private String name;

  @NotBlank
  private String effect;

  @PositiveOrZero
  private Integer spCost;

  @PositiveOrZero
  private Integer spInitial;

  @Min(value = 1)
  @Max(value = 7)
  private Integer level;

  @Min(value = 1)
  @Max(value = 3)
  private Integer mastery;

  private ChargeType chargeType;

  private ActivationType activationType;

  @Positive private Integer duration;
}
