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
import com.arkbase.utils.OperatorUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OperatorServiceTest {

  private final OperatorRepository operatorRepository = mock(OperatorRepository.class);

  private final OperatorAttributesRepository operatorAttributesRepository =
      mock(OperatorAttributesRepository.class);

  private final OperatorService operatorService =
      new OperatorService(operatorRepository, operatorAttributesRepository);

  @Test
  void shouldAddOperator() {
    OperatorCreationDTO newOperator = OperatorUtils.buildOperatorCreationDto();
    Operator operator = OperatorUtils.buildOperator();
    operator.setId(1);
    OperatorAttributes operatorAttributes = OperatorUtils.buildOperatorAttributes();
    final String codeName = newOperator.getCodeName();

    when(operatorRepository.existsByCodeName(eq(codeName))).thenReturn(false);
    when(operatorRepository.save(any(Operator.class))).thenReturn(operator);
    when(operatorAttributesRepository.save(any(OperatorAttributes.class)))
        .thenReturn(operatorAttributes);

    OperatorDTO operatorDTO = assertDoesNotThrow(() -> operatorService.addOperator(newOperator));

    assertAll(
        () -> {
          assertNotNull(operatorDTO);
          assertNotNull(operatorDTO.getAttributes());
          assertEquals(1, operatorDTO.getId());
          assertEquals(newOperator.getCodeName(), operatorDTO.getCodeName());
          assertEquals(
              Operator.Archetype.SNIPER.getArchetype(), operatorDTO.getArchetype().getArchetype());
          assertEquals(newOperator.getAttributes().getAtk(), operatorDTO.getAttributes().getAtk());
        });

    verify(operatorRepository).existsByCodeName(eq(codeName));
    verify(operatorRepository).save(any(Operator.class));
    verify(operatorAttributesRepository).save(any(OperatorAttributes.class));
  }

  @Test
  @DisplayName("should throw OperatorAlreadyExistsException when adding new operator")
  void shouldThrowOperatorAlreadyExistsExceptionWhenAddOperator() {
    OperatorCreationDTO newOperator = OperatorUtils.buildOperatorCreationDto();
    final String codeName = newOperator.getCodeName();

    when(operatorRepository.existsByCodeName(eq(codeName))).thenReturn(true);

    assertThrows(RuntimeException.class, () -> operatorService.addOperator(newOperator));

    verify(operatorRepository).existsByCodeName(eq(codeName));
    verify(operatorRepository, never()).save(any(Operator.class));
    verify(operatorAttributesRepository, never()).save(any(OperatorAttributes.class));
  }
}
