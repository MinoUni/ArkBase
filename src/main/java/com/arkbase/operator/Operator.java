package com.arkbase.operator;

import com.arkbase.converter.ArchetypeConverter;
import com.arkbase.converter.RarityConverter;
import com.arkbase.converter.SubclassConverter;
import com.arkbase.converter.TraitConverter;
import com.arkbase.enums.Rarity;
import com.arkbase.material.Material;
import com.arkbase.operator.enums.*;
import com.arkbase.skill.Skill;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "operators")
public class Operator {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "code_name", nullable = false, unique = true, length = 50)
  private String codeName;

  @Column(nullable = false, length = 10)
  @Convert(converter = ArchetypeConverter.class)
  private Archetype archetype;

  @Column(nullable = false, length = 25)
  @Convert(converter = SubclassConverter.class)
  private Subclass subclass;

  @Column(nullable = false, length = 2)
  @Convert(converter = RarityConverter.class)
  private Rarity rarity;

  @Column(nullable = false)
  @Convert(converter = TraitConverter.class)
  private Trait trait;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 6)
  private Position position;

  @Enumerated(EnumType.STRING)
  @Column(name = "attack_type", nullable = false, length = 8)
  private AttackType attackType;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "operators_materials",
      joinColumns = @JoinColumn(name = "operator_id"),
      inverseJoinColumns = @JoinColumn(name = "material_id"))
  private Set<Material> materials = new HashSet<>();

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "operators_skills",
      joinColumns = @JoinColumn(name = "operator_id"),
      inverseJoinColumns = @JoinColumn(name = "skill_id"))
  private Set<Skill> skills = new HashSet<>();

  public Operator(
      String codeName,
      String archetype,
      String subclass,
      String rarity,
      String trait,
      String position,
      String attackType) {
    this.codeName = codeName;
    this.archetype = Archetype.valueOf(archetype.toUpperCase());
    this.subclass = Subclass.valueOf(subclass.toUpperCase());
    this.rarity = Rarity.valueOf(rarity.toUpperCase());
    this.trait = Trait.valueOf(trait.toUpperCase());
    this.position = Position.valueOf(position.toUpperCase());
    this.attackType = AttackType.valueOf(attackType.toUpperCase());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Operator other)) {
      return false;
    }
    return Objects.equals(id, other.id) && Objects.equals(codeName, other.codeName);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
