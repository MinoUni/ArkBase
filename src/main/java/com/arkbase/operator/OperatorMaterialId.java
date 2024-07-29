package com.arkbase.operator;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Embeddable
public class OperatorMaterialId implements Serializable {

  @Column(name = "operator_id")
  private Integer operatorId;

  @Column(name = "material_id")
  private Integer materialId;

  private OperatorMaterialId() {}

  public OperatorMaterialId(Integer operatorId, Integer materialId) {
    this.operatorId = operatorId;
    this.materialId = materialId;
  }
  
}
