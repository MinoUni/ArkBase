package com.arkbase.operator;

import com.arkbase.material.Material;
import com.arkbase.skill.Skill;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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

  @Column(nullable = false)
  private String rarity;

  @Column(nullable = false)
  private String trait;

  @Column(nullable = false)
  private String position;

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
