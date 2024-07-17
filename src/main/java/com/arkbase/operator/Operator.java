package com.arkbase.operator;

import com.arkbase.converter.RarityConverter;
import com.arkbase.enums.Rarity;
import com.arkbase.material.Material;
import com.arkbase.skill.Skill;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "operators")
public class Operator {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "code_name", nullable = false, unique = true)
  private String codeName;

  @Column(nullable = false)
  private String archetype;

  @Column(nullable = false, length = 2)
  @Convert(converter = RarityConverter.class)
  private Rarity rarity;

  @Column(nullable = false)
  private String trait;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 6)
  private Position position;

  @Column(name = "attack_type", nullable = false)
  private String attackType;

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
}
