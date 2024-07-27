package com.arkbase.operator;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.arkbase.attribute.OperatorAttributes;
import com.arkbase.exception.OperatorAlreadyExistsException;
import com.arkbase.mapper.CustomMapper;
import com.arkbase.operator.enums.Archetype;
import com.arkbase.utils.OperatorUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OperatorServiceTest {

  private final OperatorRepository operatorRepository = mock(OperatorRepository.class);

  private final CustomMapper mapper = mock(CustomMapper.class);

  private final OperatorService operatorService = new OperatorService(operatorRepository, mapper);

  @Test
  void shouldAddOperator() {
    NewOperatorDTO newOperator = OperatorUtils.buildNewOperatorDto();
    final String codeName = newOperator.getCodeName();
    Operator operator = OperatorUtils.buildOperator();
    operator.setId(1);
    OperatorAttributes attributes = OperatorUtils.buildOperatorAttributes();
    OperatorAttributesDTO newAttributes = newOperator.getAttributes();
    OperatorDTO opDto = OperatorUtils.buildOperatorDto();

    when(operatorRepository.existsByCodeNameIgnoreCase(eq(codeName))).thenReturn(false);
    when(mapper.toOperator(eq(newOperator))).thenReturn(operator);
    when(mapper.toOperatorAttributes(eq(newAttributes))).thenReturn(attributes);
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
    verify(mapper).toOperatorDto(eq(operator), eq(attributes));
    verify(operatorRepository).save(any(Operator.class));
  }

  @Test
  @DisplayName("should throw OperatorAlreadyExistsException when adding new operator")
  void shouldThrowOperatorAlreadyExistsExceptionWhenAddOperator() {
    NewOperatorDTO newOperator = OperatorUtils.buildNewOperatorDto();
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
