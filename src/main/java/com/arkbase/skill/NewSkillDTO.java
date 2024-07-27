package com.arkbase.skill;

import com.arkbase.material.NewMaterialDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(
    exclude = {
      "description",
      "spCost",
      "level",
      "mastery",
      "chargeType",
      "activationType",
      "duration",
      "materials"
    })
@NoArgsConstructor
@AllArgsConstructor
public class NewSkillDTO {

  @NotBlank
  private String name;

  @NotBlank
  private String description;

  @PositiveOrZero
  private Integer spCost;

  @Min(value = 1)
  @Max(value = 7)
  private Integer level;

  @Min(value = 1)
  @Max(value = 3)
  private Integer mastery;

  private ChargeType chargeType;

  private ActivationType activationType;

  @Positive
  private Integer duration;

  @Valid
  private Set<NewMaterialDTO> materials;
}
