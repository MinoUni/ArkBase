package com.arkbase.operator;

import com.arkbase.attribute.OperatorAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OperatorService {

  private final OperatorRepository operatorRepository;
  private final OperatorAttributesRepository operatorAttributesRepository;

  @Transactional
  public OperatorDTO addOperator(OperatorCreationDTO newOperator) {
    if (operatorRepository.existsByCodeName(newOperator.getCodeName())) {
      throw new RuntimeException();
    }
    Operator operator =
        new Operator(
            newOperator.getCodeName(),
            newOperator.getArchetype(),
            newOperator.getSubclass(),
            newOperator.getRarity(),
            newOperator.getTrait(),
            newOperator.getPosition(),
            newOperator.getAttackType());

    operator = operatorRepository.save(operator);
    var atr = newOperator.getAttributes();

    OperatorAttributes operatorAttributes =
        new OperatorAttributes(
            atr.getHp(),
            atr.getAtk(),
            atr.getDef(),
            atr.getRes(),
            atr.getBlock(),
            atr.getDeploymentCost(),
            atr.getRedeploymentTime(),
            atr.getAspd());

    operatorAttributes.setOperator(operator);
    operatorAttributes = operatorAttributesRepository.save(operatorAttributes);

    return OperatorDTO.builder()
        .id(operator.getId())
        .codeName(operator.getCodeName())
        .archetype(operator.getArchetype())
        .subclass(operator.getSubclass())
        .rarity(operator.getRarity())
        .trait(operator.getTrait())
        .attackType(operator.getAttackType())
        .position(operator.getPosition())
        .attackType(operator.getAttackType())
        .attributes(
            OperatorAttributesDTO.builder()
                .hp(operatorAttributes.getHp())
                .atk(operatorAttributes.getAtk())
                .def(operatorAttributes.getDef())
                .res(operatorAttributes.getRes())
                .block(operatorAttributes.getBlock())
                .deploymentCost(operatorAttributes.getDeploymentCost())
                .redeploymentTime(operatorAttributes.getRedeploymentTime())
                .aspd(operatorAttributes.getAspd())
                .build())
        .build();
  }
}
