package com.arkbase.operator;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.arkbase.attribute.OperatorAttributes;
import com.arkbase.exception.OperatorAlreadyExistsException;
import com.arkbase.mapper.CustomMapper;
import com.arkbase.operator.enums.Archetype;
import com.arkbase.skill.NewSkillDTO;
import com.arkbase.skill.SkillRepository;
import com.arkbase.utils.TestUtils;
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
    NewOperatorDTO newOperator = TestUtils.buildNewOperatorDto();
    final String codeName = newOperator.getCodeName();
    Operator operator = TestUtils.buildOperator();
    operator.setId(1);
    OperatorAttributes attributes = TestUtils.buildOperatorAttributes();
    OperatorAttributesDTO newAttributes = newOperator.getAttributes();
    OperatorDTO opDto = TestUtils.buildOperatorDto();

    when(operatorRepository.existsByCodeNameIgnoreCase(eq(codeName))).thenReturn(false);
    when(mapper.toOperator(eq(newOperator))).thenReturn(operator);
    when(mapper.toOperatorAttributes(eq(newAttributes))).thenReturn(attributes);

    when(mapper.toSkill(any(NewSkillDTO.class))).thenReturn(TestUtils.buildSkill("SkillName"));
    when(skillRepository.existsByNameIgnoreCase(any(String.class))).thenReturn(false);

    when(operatorRepository.save(any(Operator.class))).thenReturn(operator);
    when(mapper.toOperatorDto(eq(operator), eq(attributes))).thenReturn(opDto);

    OperatorDTO operatorDTO = assertDoesNotThrow(() -> operatorService.addOperator(newOperator));

    assertAll(
        () -> {
          assertNotNull(operatorDTO);
          assertNotNull(operatorDTO.getAttributes());
          assertEquals(1, operatorDTO.getId());
          assertEquals(newOperator.getCodeName(), operatorDTO.getCodeName());
          assertEquals(Archetype.SNIPER.getArchetype(), operatorDTO.getArchetype().getArchetype());
          assertEquals(newAttributes.getAtk(), operatorDTO.getAttributes().getAtk());
        });

    verify(operatorRepository).existsByCodeNameIgnoreCase(eq(codeName));
    verify(mapper).toOperator(eq(newOperator));
    verify(mapper).toOperatorAttributes(eq(newAttributes));
    verify(mapper, times(3)).toSkill(any(NewSkillDTO.class));
    verify(skillRepository, times(3)).existsByNameIgnoreCase(any(String.class));
    verify(mapper).toOperatorDto(eq(operator), eq(attributes));
    verify(operatorRepository).save(any(Operator.class));
  }

  @Test
  @DisplayName("should throw OperatorAlreadyExistsException when adding new operator")
  void shouldThrowOperatorAlreadyExistsExceptionWhenAddOperator() {
    NewOperatorDTO newOperator = TestUtils.buildNewOperatorDto();
    final String codeName = newOperator.getCodeName();

    when(operatorRepository.existsByCodeNameIgnoreCase(eq(codeName))).thenReturn(true);

    OperatorAlreadyExistsException e =
        assertThrows(
            OperatorAlreadyExistsException.class, () -> operatorService.addOperator(newOperator));

    assertEquals(String.format("Operator {%s} already exists!", codeName), e.getMessage());

    verify(operatorRepository).existsByCodeNameIgnoreCase(eq(codeName));
    verify(mapper, never()).toOperator(eq(newOperator));
    verify(mapper, never()).toOperatorAttributes(eq(newOperator.getAttributes()));
    verify(mapper, never()).toOperatorDto(any(Operator.class), any(OperatorAttributes.class));
    verify(operatorRepository, never()).save(any(Operator.class));
  }
}
