package com.arkbase.material;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(exclude = {"rarity", "description", "quantity"})
@NoArgsConstructor
@AllArgsConstructor
public class NewMaterialDTO {

  @NotBlank
  private String name;

  @NotNull
  private Rarity rarity;

  @NotBlank
  private String description;

  @Positive
  private Integer quantity;
}
