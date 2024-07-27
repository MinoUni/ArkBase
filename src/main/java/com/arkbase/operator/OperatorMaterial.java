package com.arkbase.operator;

import com.arkbase.material.Material;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@Entity
@Table(name = "operators_materials")
public class OperatorMaterial {

  @Setter(AccessLevel.NONE)
  @EmbeddedId
  private OperatorMaterialId id;

  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("operatorId")
  private Operator operator;

  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("materialId")
  private Material material;

  @Column(nullable = false)
  private Integer quantity;

  private OperatorMaterial() {}

  public OperatorMaterial(Operator operator, Material material, Integer quantity) {
    this.id = new OperatorMaterialId(operator.getId(), material.getId());
    this.operator = operator;
    this.material = material;
    this.quantity = quantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof OperatorMaterial that)) {
      return false;
    }
    return Objects.equals(operator, that.operator) && Objects.equals(material, that.material);
  }

  @Override
  public int hashCode() {
    return Objects.hash(operator, material);
  }
}
