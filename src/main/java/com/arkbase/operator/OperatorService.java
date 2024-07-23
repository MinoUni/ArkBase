package com.arkbase.operator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OperatorService {

  @Transactional
  public OperatorDTO addOperator(OperatorCreationDTO operator) {
    return null;
  }
}
