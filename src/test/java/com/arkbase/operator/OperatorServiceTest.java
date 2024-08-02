package com.arkbase.operator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.arkbase.attribute.OperatorAttributes;
import com.arkbase.enums.Rarity;
import com.arkbase.exception.OperatorAlreadyExistsException;
import com.arkbase.exception.OperatorNotFoundException;
import com.arkbase.mapper.CustomMapper;
import com.arkbase.skill.NewSkillDTO;
import com.arkbase.skill.Skill;
import com.arkbase.skill.SkillRepository;
import com.arkbase.utils.TestUtils;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OperatorServiceTest {

  private final OperatorRepository operatorRepository = mock(OperatorRepository.class);

  private final SkillRepository skillRepository = mock(SkillRepository.class);

  private final CustomMapper mapper = mock(CustomMapper.class);

  private final OperatorService operatorService =
      new OperatorService(operatorRepository, skillRepository, mapper);

  @Test
  void shouldAddOperator() {
    String codename = "Mlynar";
    String skillNameOne = "Attack Boost Alpha";
    String skillNameTwo = "Attack Boost Beta";
    NewOperatorDTO newOperator =
        NewOperatorDTO.builder()
            .codeName(codename)
            .rarity(Rarity.SIX_STAR)
            .attributes(OperatorAttributesDTO.builder().build())
            .skills(
                Set.of(
                    NewSkillDTO.builder().name(skillNameOne).build(),
                    NewSkillDTO.builder().name(skillNameTwo).build()))
            .build();
    Operator operator =
        Operator.builder()
            .codeName(codename)
            .rarity(Rarity.SIX_STAR)
            .attributes(OperatorAttributes.builder().build())
            .build();
    OperatorDTO operatorDto = OperatorDTO.builder().build();
    int skillId = 1;
    Skill skillOne = TestUtils.buildSkill(skillNameOne);
    Skill skillTwo = TestUtils.buildSkill(skillNameTwo);

    when(operatorRepository.existsByCodeNameIgnoreCase(eq(codename))).thenReturn(false);
    when(mapper.toOperator(eq(newOperator))).thenReturn(operator);
    when(mapper.toOperatorAttributes(eq(newOperator.attributes())))
        .thenReturn(operator.getAttributes());
    when(skillRepository.existsByNameIgnoreCase(eq(skillNameOne))).thenReturn(true);
    when(skillRepository.getIdByName(eq(skillNameOne))).thenReturn(skillId);
    when(skillRepository.getReferenceById(eq(skillId))).thenReturn(skillOne);
    when(skillRepository.existsByNameIgnoreCase(eq(skillNameTwo))).thenReturn(false);
    when(mapper.toSkill(any(NewSkillDTO.class))).thenReturn(skillTwo);
    when(operatorRepository.save(eq(operator))).thenReturn(operator);
    when(mapper.toOperatorDto(eq(operator), eq(operator.getAttributes()))).thenReturn(operatorDto);

    assertDoesNotThrow(() -> operatorService.addOperator(newOperator));

    verify(operatorRepository).existsByCodeNameIgnoreCase(eq(codename));
    verify(mapper).toOperator(eq(newOperator));
    verify(mapper).toOperatorAttributes(eq(newOperator.attributes()));
    verify(skillRepository, times(2)).existsByNameIgnoreCase(any(String.class));
    verify(skillRepository).getIdByName(eq(skillNameOne));
    verify(skillRepository).getReferenceById(eq(skillId));
    verify(mapper).toSkill(any(NewSkillDTO.class));
    verify(mapper).toOperatorDto(eq(operator), eq(operator.getAttributes()));
    verify(operatorRepository).save(any(Operator.class));
  }

  @Test
  @DisplayName("should throw OperatorAlreadyExistsException when adding new operator")
  void shouldThrowOperatorAlreadyExistsExceptionWhenAddOperator() {
    NewOperatorDTO newOperator = TestUtils.buildNewOperatorDto();
    final String codeName = newOperator.codeName();

    when(operatorRepository.existsByCodeNameIgnoreCase(eq(codeName))).thenReturn(true);

    OperatorAlreadyExistsException e =
        assertThrows(
            OperatorAlreadyExistsException.class, () -> operatorService.addOperator(newOperator));

    assertEquals(String.format("Operator {%s} already exists!", codeName), e.getMessage());

    verify(operatorRepository).existsByCodeNameIgnoreCase(eq(codeName));
    verify(mapper, never()).toOperator(eq(newOperator));
    verify(mapper, never()).toOperatorAttributes(eq(newOperator.attributes()));
    verify(mapper, never()).toOperatorDto(any(Operator.class), any(OperatorAttributes.class));
    verify(operatorRepository, never()).save(any(Operator.class));
  }

  @Test
  void shouldFindOperatorById() {
    final int id = 1;
    Operator operator = TestUtils.buildOperator();
    operator.setAttributes(TestUtils.buildOperatorAttributes());
    OperatorDTO operatorDTO = TestUtils.buildOperatorDto();

    when(operatorRepository.findById(eq(id))).thenReturn(Optional.of(operator));
    when(mapper.toOperatorDto(eq(operator), eq(operator.getAttributes()))).thenReturn(operatorDTO);

    assertDoesNotThrow(() -> operatorService.findById(id));

    verify(operatorRepository).findById(eq(id));
    verify(mapper).toOperatorDto(eq(operator), eq(operator.getAttributes()));
  }

  @Test
  void shouldThrowOperatorNotFoundExceptionWhenFindById() {
    final int id = 1;

    when(operatorRepository.findById(eq(id))).thenThrow(new OperatorNotFoundException(id));

    assertThrows(OperatorNotFoundException.class, () -> operatorService.findById(id));

    verify(operatorRepository).findById(eq(id));
    verify(mapper, never()).toOperatorDto(any(Operator.class), any(OperatorAttributes.class));
  }

  @Test
  void shouldFindOperatorByCodeName() {
    final String codeName = "Ray";
    Operator operator = TestUtils.buildOperator();
    operator.setAttributes(TestUtils.buildOperatorAttributes());
    OperatorDTO operatorDTO = TestUtils.buildOperatorDto();

    when(operatorRepository.findByCodeNameIgnoreCase(eq(codeName)))
        .thenReturn(Optional.of(operator));
    when(mapper.toOperatorDto(eq(operator), eq(operator.getAttributes()))).thenReturn(operatorDTO);

    assertDoesNotThrow(() -> operatorService.findByCodeName(codeName));

    verify(operatorRepository).findByCodeNameIgnoreCase(eq(codeName));
    verify(mapper).toOperatorDto(eq(operator), eq(operator.getAttributes()));
  }

  @Test
  void shouldThrowOperatorNotFoundExceptionWhenFindByCodeName() {
    final String codeName = "Ray";

    when(operatorRepository.findByCodeNameIgnoreCase(eq(codeName)))
        .thenThrow(new OperatorNotFoundException(codeName));

    assertThrows(OperatorNotFoundException.class, () -> operatorService.findByCodeName(codeName));

    verify(operatorRepository).findByCodeNameIgnoreCase(eq(codeName));
    verify(mapper, never()).toOperatorDto(any(Operator.class), any(OperatorAttributes.class));
  }
}
