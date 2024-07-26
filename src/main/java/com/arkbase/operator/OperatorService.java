package com.arkbase.operator;

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
  private final OperatorMapper mapper;

  @Transactional
  public OperatorDTO addOperator(NewOperatorDTO newOperator)
      throws OperatorAlreadyExistsException {
    final String codeName = newOperator.getCodeName();
    if (operatorRepository.existsByCodeNameIgnoreCase(codeName)) {
      throw new OperatorAlreadyExistsException(codeName);
    }
    var operator = mapper.toOperator(newOperator);
    operator.addAttributes(mapper.toOperatorAttributes(newOperator.getAttributes()));
    operator = operatorRepository.save(operator);
    return mapper.toOperatorDto(operator, operator.getAttributes());
  }
}
