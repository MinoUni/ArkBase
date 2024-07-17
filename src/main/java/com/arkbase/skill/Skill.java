package com.arkbase.skill;

import com.arkbase.material.Material;
import com.arkbase.operator.Operator;
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
@Table(name = "skills")
public class Skill {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(unique = true, nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  @Column(name = "sp_cost", nullable = false)
  private Integer spCost;

  @Column(nullable = false)
  private Integer level;

  @Column(nullable = false)
  private Integer mastery;

  @Column(name = "recovery_type", nullable = false)
  private String recoveryType;

  @Column(name = "activation_type", nullable = false)
  private String activationType;

  @Column(nullable = false)
  private Integer duration;

  @ManyToMany(mappedBy = "skills")
  private Set<Operator> operators = new HashSet<>();

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "skills_materials",
      joinColumns = @JoinColumn(name = "skill_id"),
      inverseJoinColumns = @JoinColumn(name = "material_id"))
  private Set<Material> materials = new HashSet<>();
}
