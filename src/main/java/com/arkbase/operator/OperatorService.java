package com.arkbase.operator;

import com.arkbase.attribute.OperatorAttributes;
import com.arkbase.exception.OperatorAlreadyExistsException;
import com.arkbase.mapper.OperatorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OperatorService {

  private final OperatorRepository operatorRepository;
  private final OperatorAttributesRepository operatorAttributesRepository;
  private final OperatorMapper mapper;

  @Transactional
  public OperatorDTO addOperator(OperatorCreationDTO newOperator)
      throws OperatorAlreadyExistsException {
    final String codeName = newOperator.getCodeName();
    if (operatorRepository.existsByCodeName(codeName)) {
      throw new OperatorAlreadyExistsException(codeName);
    }
    Operator operator = mapper.toOperator(newOperator);
    operator = operatorRepository.save(operator);

    OperatorAttributes attributes = mapper.toOperatorAttributes(newOperator.getAttributes());
    attributes.setOperator(operator);
    attributes = operatorAttributesRepository.save(attributes);

    return mapper.toOperatorDto(operator, attributes);
  }
}
