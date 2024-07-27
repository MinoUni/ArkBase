package com.arkbase.skill;

import com.arkbase.material.Material;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@Entity
@Table(name = "skills_materials")
public class SkillMaterial {

  @Setter(AccessLevel.NONE)
  @EmbeddedId
  private SkillMaterialId id;

  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("skillId")
  private Skill skill;

  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("materialId")
  private Material material;

  @Column(nullable = false)
  private Integer quantity;

  private SkillMaterial() {}

  public SkillMaterial(Skill skill, Material material, Integer quantity) {
    this.id = new SkillMaterialId(skill.getId(), material.getId());
    this.skill = skill;
    this.material = material;
    this.quantity = quantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SkillMaterial that)) {
      return false;
    }
    return Objects.equals(skill, that.skill) && Objects.equals(material, that.material);
  }

  @Override
  public int hashCode() {
    return Objects.hash(skill, material);
  }
}
