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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
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
  private String description;

  @Column(name = "sp_cost", nullable = false)
  private Integer spCost;

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
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "skills_materials",
      joinColumns = @JoinColumn(name = "skill_id"),
      inverseJoinColumns = @JoinColumn(name = "material_id"))
  private Set<Material> materials = new HashSet<>();

  public Skill addMaterial(Material material) {
    materials.add(material);
    material.getSkills().add(this);
    return this;
  }

  public void removeMaterial(Material material) {
    materials.remove(material);
    material.getSkills().remove(this);
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
