package com.arkbase.material;

import com.arkbase.operator.Operator;
import com.arkbase.skill.Skill;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "materials")
public class Material {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private String rarity;

  @Column(nullable = false)
  private String description;

  @ManyToMany(mappedBy = "materials")
  private Set<Operator> operators = new HashSet<>();

  @ManyToMany(mappedBy = "materials")
  private Set<Skill> skills = new HashSet<>();
}
