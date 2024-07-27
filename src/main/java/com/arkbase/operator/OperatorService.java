package com.arkbase.operator;

import com.arkbase.exception.OperatorAlreadyExistsException;
import com.arkbase.mapper.CustomMapper;
import com.arkbase.material.NewMaterialDTO;
import com.arkbase.skill.NewSkillDTO;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OperatorService {

  private final OperatorRepository repository;
  private final CustomMapper mapper;

  @Transactional
  public OperatorDTO addOperator(NewOperatorDTO newOperator) throws OperatorAlreadyExistsException {
    final String codeName = newOperator.getCodeName();
    if (repository.existsByCodeNameIgnoreCase(codeName)) {
      throw new OperatorAlreadyExistsException(codeName);
    }
    var operator = mapper.toOperator(newOperator);
    operator.setAttributes(mapper.toOperatorAttributes(newOperator.getAttributes()));
    addMaterials(operator, newOperator.getMaterials());
    addSkills(operator, newOperator.getSkills());
    operator = repository.save(operator);
    return mapper.toOperatorDto(operator, operator.getAttributes());
  }

  private void addMaterials(Operator operator, Set<NewMaterialDTO> materials) {
    if (materials == null || materials.isEmpty()) {
      return;
    }
    for (NewMaterialDTO newMaterial : materials) {
      operator.addMaterial(mapper.toMaterial(newMaterial), newMaterial.getQuantity());
    }
  }

  private void addSkills(Operator operator, Set<NewSkillDTO> skills) {
    if (skills == null || skills.isEmpty()) {
      return;
    }
    for (NewSkillDTO newSkill : skills) {
      operator.addSkill(mapper.toSkill(newSkill));
    }
  }
}
