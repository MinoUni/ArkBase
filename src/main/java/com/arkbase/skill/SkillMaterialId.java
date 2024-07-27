package com.arkbase.skill;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Embeddable
public class SkillMaterialId implements Serializable {

  @Column(name = "skill_id")
  private Integer skillId;

  @Column(name = "material_id")
  private Integer materialId;

  private SkillMaterialId() {}

  public SkillMaterialId(Integer skillId, Integer materialId) {
    this.skillId = skillId;
    this.materialId = materialId;
  }
}
