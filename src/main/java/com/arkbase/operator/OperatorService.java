package com.arkbase.operator;

import com.arkbase.exception.OperatorAlreadyExistsException;
import com.arkbase.mapper.CustomMapper;
import com.arkbase.material.Material;
import com.arkbase.material.MaterialRepository;
import com.arkbase.material.NewMaterialDTO;
import com.arkbase.skill.NewSkillDTO;
import com.arkbase.skill.Skill;
import com.arkbase.skill.SkillRepository;
import java.util.HashSet;
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
  private final MaterialRepository materialRepository;
  private final CustomMapper mapper;

  @Transactional
  public OperatorDTO addOperator(NewOperatorDTO newOperator) throws OperatorAlreadyExistsException {
    final String codeName = newOperator.getCodeName();
    if (operatorRepository.existsByCodeNameIgnoreCase(codeName)) {
      throw new OperatorAlreadyExistsException(codeName);
    }
    var operator = mapper.toOperator(newOperator);
    operator.setAttributes(mapper.toOperatorAttributes(newOperator.getAttributes()));
    Set<Material> materials = extractMaterials(newOperator);
    addSkills(operator, newOperator.getSkills(), materials);
    addMaterials(operator, newOperator.getMaterials(), materials);
    operator = operatorRepository.save(operator);
    return mapper.toOperatorDto(operator, operator.getAttributes());
  }

  private Set<Material> extractMaterials(NewOperatorDTO operator) {
    Set<NewMaterialDTO> materialDtos = new HashSet<>(operator.getMaterials());
    operator.getSkills().forEach(skill -> materialDtos.addAll(skill.getMaterials()));
    Set<Material> materials = new HashSet<>(materialDtos.size());
    for (NewMaterialDTO materialDto : materialDtos) {
      String name = materialDto.getName();
      if (materialRepository.existsByNameIgnoreCase(name)) {
        materials.add(materialRepository.getReferenceById(materialRepository.getIdByName(name)));
      } else {
        materials.add(mapper.toMaterial(materialDto));
      }
    }
    return materials;
  }

  private void addMaterials(
      Operator operator, Set<NewMaterialDTO> materials, Set<Material> materialsList) {
    if (materials == null || materials.isEmpty()) {
      return;
    }
    for (NewMaterialDTO materialDto : materials) {
      Material material =
          materialsList.stream()
              .filter(m -> m.getName().equals(materialDto.getName()))
              .findFirst()
              .get();
      operator.addMaterial(material, materialDto.getQuantity());
    }
  }

  private void addSkills(Operator operator, Set<NewSkillDTO> skills, Set<Material> materialsList) {
    if (skills == null || skills.isEmpty()) {
      return;
    }
    for (NewSkillDTO skillDto : skills) {
      Skill skill = mapper.toSkill(skillDto);
      String name = skill.getName();
      if (skillRepository.existsByNameIgnoreCase(name)) {
        operator.addSkill(skillRepository.getReferenceById(skillRepository.getIdByName(name)));
      } else {
        addSkillMaterials(skill, skillDto.getMaterials(), materialsList);
        operator.addSkill(skill);
      }
    }
  }

  private void addSkillMaterials(
      Skill skill, Set<NewMaterialDTO> materials, Set<Material> materialsList) {
    if (materials == null || materials.isEmpty()) {
      return;
    }
    for (NewMaterialDTO materialDto : materials) {
      Material material =
          materialsList.stream()
              .filter(m -> m.getName().equals(materialDto.getName()))
              .findFirst()
              .get();
      skill.addMaterial(material, materialDto.getQuantity());
    }
  }
}
