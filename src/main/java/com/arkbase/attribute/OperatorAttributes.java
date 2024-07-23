package com.arkbase.attribute;

import com.arkbase.operator.Operator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "operator_attributes")
public class OperatorAttributes {

  @Id private Integer id;

  @Column(nullable = false)
  private Integer hp;

  @Column(nullable = false)
  private Integer atk;

  @Column(nullable = false)
  private Integer def;

  @Column(nullable = false)
  private Integer res;

  @Column(nullable = false)
  private Integer block;

  @Column(nullable = false)
  private Integer deploymentCost;

  @Column(nullable = false)
  private String redeploymentTime;

  @Column(nullable = false)
  private String aspd;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  private Operator operator;

  public OperatorAttributes(
      Integer hp,
      Integer atk,
      Integer def,
      Integer res,
      Integer block,
      Integer deploymentCost,
      String redeploymentTime,
      String aspd) {
    this.hp = hp;
    this.atk = atk;
    this.def = def;
    this.res = res;
    this.block = block;
    this.deploymentCost = deploymentCost;
    this.redeploymentTime = redeploymentTime;
    this.aspd = aspd;
  }
}
