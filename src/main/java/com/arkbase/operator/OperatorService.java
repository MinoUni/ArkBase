package com.arkbase.operator;

import com.arkbase.exception.OperatorAlreadyExistsException;
import com.arkbase.exception.OperatorNotFoundException;
import com.arkbase.exception.SkillNotFoundException;
import com.arkbase.mapper.CustomMapper;
import com.arkbase.skill.NewSkillDTO;
import com.arkbase.skill.Skill;
import com.arkbase.skill.SkillDTO;
import com.arkbase.skill.SkillRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OperatorService {

  private final OperatorRepository operatorRepository;
  private final SkillRepository skillRepository;
  private final CustomMapper mapper;

  @Transactional
  public OperatorDTO createOperator(NewOperatorDTO newOperator) {
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

  public Page<OperatorDTO> findAllOperators(int page, int size) {
    PageRequest request = PageRequest.of(page, size, Sort.by("code_name"));
    var list =
        operatorRepository.findAll(request).stream()
            .map(operator -> mapper.toOperatorDto(operator, operator.getAttributes()))
            .toList();
    return new PageImpl<>(list);
  }

  public OperatorDTO findOperatorById(Integer id) {
    return operatorRepository
        .findById(id)
        .map(operator -> mapper.toOperatorDto(operator, operator.getAttributes()))
        .orElseThrow(() -> new OperatorNotFoundException(id));
  }

  public OperatorDTO findOperatorByCodeName(String codeName) {
    return operatorRepository
        .findByCodeNameIgnoreCase(codeName)
        .map(operator -> mapper.toOperatorDto(operator, operator.getAttributes()))
        .orElseThrow(() -> new OperatorNotFoundException(codeName));
  }

  @Transactional
  public List<SkillDTO> addSkillToOperator(int operatorId, NewSkillDTO skillDto) {
    Operator operator = getOperator(operatorId);
    Skill skill = getSkillReferenceOrMap(skillDto);
    operator.addSkill(skill);
    operator = operatorRepository.save(operator);
    return operator.getSkills().stream().map(mapper::toSkillDto).collect(Collectors.toList());
  }

  @Transactional
  public List<SkillDTO> addExistingSkillToOperator(int operatorId, int skillId) {
    if (!skillRepository.existsById(skillId)) {
      throw new SkillNotFoundException(skillId);
    }
    Operator operator = getOperator(operatorId);
    operator.addSkill(skillRepository.getReferenceById(skillId));
    operator = operatorRepository.save(operator);
    return operator.getSkills().stream().map(mapper::toSkillDto).collect(Collectors.toList());
  }

  @Transactional
  public List<SkillDTO> removeSkillFromOperator(int operatorId, int skillId) {
    if (!skillRepository.existsById(skillId)) {
      throw new SkillNotFoundException(skillId);
    }
    Operator operator = getOperator(operatorId);
    Skill skill = skillRepository.getReferenceById(skillId);
    operator.removeSkill(skill);
    operator = operatorRepository.save(operator);
    return operator.getSkills().stream().map(mapper::toSkillDto).collect(Collectors.toList());
  }

  @Transactional(isolation = Isolation.REPEATABLE_READ)
  public OperatorDetailsDTO updateOperatorDetails(
      int operatorId, OperatorDetailsUpdate operatorUpdate) {
    Operator operator = getOperator(operatorId);
    mapper.updateOperatorFromDto(operatorUpdate, operator);
    operator = operatorRepository.save(operator);
    return mapper.toOperatorDetailsDto(operator);
  }

  private void setSkills(Operator operator, Set<NewSkillDTO> skills) {
    if (skills == null || skills.isEmpty()) {
      return;
    }
    skills.stream().map(this::getSkillReferenceOrMap).forEach(operator::addSkill);
  }

  private Skill getSkillReferenceOrMap(NewSkillDTO skillDto) {
    String name = skillDto.getName();
    if (skillRepository.existsByNameIgnoreCase(name)) {
      Integer skillId = skillRepository.getIdByName(name);
      return skillRepository.getReferenceById(skillId);
    }
    return mapper.toSkill(skillDto);
  }

  private Operator getOperator(int operatorId) {
    return operatorRepository
        .findById(operatorId)
        .orElseThrow(() -> new OperatorNotFoundException(operatorId));
  }
}
