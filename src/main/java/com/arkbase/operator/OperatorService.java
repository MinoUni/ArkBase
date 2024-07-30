package com.arkbase.operator;

import com.arkbase.exception.OperatorAlreadyExistsException;
import com.arkbase.exception.OperatorNotFoundException;
import com.arkbase.mapper.CustomMapper;
import com.arkbase.skill.NewSkillDTO;
import com.arkbase.skill.Skill;
import com.arkbase.skill.SkillRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OperatorService {

  private final OperatorRepository operatorRepository;
  private final SkillRepository skillRepository;
  private final CustomMapper mapper;

  @Transactional
  public OperatorDTO addOperator(NewOperatorDTO newOperator) throws OperatorAlreadyExistsException {
    final String codeName = newOperator.codeName();
    if (operatorRepository.existsByCodeNameIgnoreCase(codeName)) {
      throw new OperatorAlreadyExistsException(codeName);
    }
    var operator = mapper.toOperator(newOperator);
    operator.setAttributes(mapper.toOperatorAttributes(newOperator.attributes()));
    setSkills(operator, newOperator.skills());
    operator = operatorRepository.save(operator);
    return mapper.toOperatorDto(operator, operator.getAttributes());
  }

  public OperatorDTO findById(Integer id) {
    return operatorRepository
        .findById(id)
        .map(operator -> mapper.toOperatorDto(operator, operator.getAttributes()))
        .orElseThrow(() -> new OperatorNotFoundException(id));
  }

  public OperatorDTO findByCodeName(String codeName) {
    return operatorRepository
        .findByCodeNameIgnoreCase(codeName)
        .map(operator -> mapper.toOperatorDto(operator, operator.getAttributes()))
        .orElseThrow(() -> new OperatorNotFoundException(codeName));
  }

  private void setSkills(Operator operator, Set<NewSkillDTO> skills) {
    if (skills == null || skills.isEmpty()) {
      return;
    }
    skills.forEach(
        skillDto -> {
          String name = skillDto.getName();
          if (skillRepository.existsByNameIgnoreCase(name)) {
            Skill skillRef = skillRepository.getReferenceById(skillRepository.getIdByName(name));
            operator.addSkill(skillRef);
          } else {
            operator.addSkill(mapper.toSkill(skillDto));
          }
        });
  }
}
