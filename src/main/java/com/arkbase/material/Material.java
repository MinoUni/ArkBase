package com.arkbase.material;

import com.arkbase.enums.Rarity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "materials")
public class Material {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, unique = true, length = 40)
  private String name;

  @Column(nullable = false, length = 2)
  @Convert(converter = MaterialRarityConverter.class)
  private Rarity rarity;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String description;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Material material)) {
      return false;
    }
    return Objects.equals(id, material.id) && Objects.equals(name, material.name);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
