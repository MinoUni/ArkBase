package com.arkbase.material;

import com.arkbase.enums.Rarity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDTO {

  private Integer id;

  private String name;

  private Rarity rarity;

  private String description;
}
