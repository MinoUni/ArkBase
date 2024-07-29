package com.arkbase.skill;

import com.arkbase.material.Material;
import com.arkbase.operator.Operator;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "skills")
public class Skill {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(unique = true, nullable = false)
  private String name;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String effect;

  @Column(name = "sp_cost", nullable = false)
  private Integer spCost;

  @Column(name = "sp_initial", nullable = false)
  private Integer spInitial;

  @Column(nullable = false)
  private Integer level;

  @Column(nullable = false)
  private Integer mastery;

  @Enumerated(EnumType.STRING)
  @Column(name = "charge_type", nullable = false)
  private ChargeType chargeType;

  @Enumerated(EnumType.STRING)
  @Column(name = "activation_type", nullable = false)
  private ActivationType activationType;

  @Column(nullable = false)
  private Integer duration;

  @Builder.Default
  @ToString.Exclude
  @ManyToMany(mappedBy = "skills")
  private Set<Operator> operators = new HashSet<>();

  @Builder.Default
  @ToString.Exclude
  @Setter(AccessLevel.NONE)
  @OneToMany(mappedBy = "skill", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SkillMaterial> materials = new ArrayList<>();

  public Skill addMaterial(Material material, Integer quantity) {
    SkillMaterial skillMaterial = new SkillMaterial(this, material, quantity);
    materials.add(skillMaterial);
    return this;
  }

  public void removeMaterial(Material material) {
    for (Iterator<SkillMaterial> iterator = materials.iterator(); iterator.hasNext(); ) {
      SkillMaterial skillMaterial = iterator.next();
      if (skillMaterial.getSkill().equals(this) && skillMaterial.getMaterial().equals(material)) {
        iterator.remove();
        skillMaterial.setSkill(null);
        skillMaterial.setMaterial(null);
      }
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Skill skill)) {
      return false;
    }
    return Objects.equals(id, skill.id) && Objects.equals(name, skill.name);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
