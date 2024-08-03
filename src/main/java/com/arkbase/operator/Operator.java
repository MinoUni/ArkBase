package com.arkbase.operator;

import com.arkbase.attribute.OperatorAttributes;
import com.arkbase.converter.ArchetypeConverter;
import com.arkbase.converter.RarityConverter;
import com.arkbase.converter.SubclassConverter;
import com.arkbase.converter.TraitConverter;
import com.arkbase.enums.Rarity;
import com.arkbase.exception.OperatorSkillSlotsException;
import com.arkbase.exception.SkillAlreadySlottedException;
import com.arkbase.material.Material;
import com.arkbase.operator.enums.Archetype;
import com.arkbase.operator.enums.AttackType;
import com.arkbase.operator.enums.Position;
import com.arkbase.operator.enums.Subclass;
import com.arkbase.operator.enums.Trait;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

@Getter
@Setter
@Builder
@ToString
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

  @Setter(AccessLevel.NONE)
  @OneToOne(mappedBy = "operator", cascade = CascadeType.ALL, orphanRemoval = true)
  private OperatorAttributes attributes;

  @Builder.Default
  @ToString.Exclude
  @Setter(AccessLevel.NONE)
  @OneToMany(mappedBy = "operator", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OperatorMaterial> materials = new ArrayList<>();

  @Builder.Default
  @ToString.Exclude
  @Setter(AccessLevel.NONE)
  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "operators_skills",
      joinColumns = @JoinColumn(name = "operator_id"),
      inverseJoinColumns = @JoinColumn(name = "skill_id"))
  private Set<Skill> skills = new HashSet<>();

  public void setAttributes(OperatorAttributes attributes) {
    if (attributes == null) {
      if (this.attributes != null) {
        this.attributes.setOperator(null);
      }
    } else {
      attributes.setOperator(this);
    }
    this.attributes = attributes;
  }

  public void addMaterial(Material material, Integer materialQuantity) {
    OperatorMaterial opMaterial = new OperatorMaterial(this, material, materialQuantity);
    materials.add(opMaterial);
  }

  public void removeMaterial(Material material) {
    for (Iterator<OperatorMaterial> iterator = materials.iterator(); iterator.hasNext(); ) {
      OperatorMaterial opMaterial = iterator.next();
      if (opMaterial.getOperator().equals(this) && opMaterial.getMaterial().equals(material)) {
        iterator.remove();
        opMaterial.setOperator(null);
        opMaterial.setMaterial(null);
      }
    }
  }

  public void addSkill(Skill skill) {
    if (skills.contains(skill)) {
      throw new SkillAlreadySlottedException(this.id, skill.getName());
    }
    fillSlotAccordingToRarity(skill);
    skill.getOperators().add(this);
  }

  public void removeSkill(Skill skill) {
    skills.remove(skill);
    skill.getOperators().remove(this);
  }

  private void fillSlotAccordingToRarity(Skill skill) {
    switch (rarity) {
      case SIX_STAR -> fillFreeSlotIfPresent(3, skill);
      case FIVE_STAR, FOUR_STAR -> fillFreeSlotIfPresent(2, skill);
      case THREE_STAR -> fillFreeSlotIfPresent(1, skill);
      case TWO_STAR, ONE_STAR -> throw new OperatorSkillSlotsException(this.id);
    }
  }

  private void fillFreeSlotIfPresent(final int MAX_SLOTS, Skill skill) {
    if (skills.size() == MAX_SLOTS) {
      throw new OperatorSkillSlotsException(this.id);
    }
    skills.add(skill);
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
