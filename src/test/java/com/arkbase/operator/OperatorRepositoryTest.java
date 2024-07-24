package com.arkbase.operator;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.arkbase.attribute.OperatorAttributes;
import com.arkbase.utils.OperatorUtils;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class OperatorRepositoryTest {

  @Autowired private EntityManager entityManager;

  @Autowired private OperatorRepository operatorRepository;

  @Autowired private OperatorAttributesRepository attributesRepository;

  @Test
  void shouldSaveOperator() {
    Operator operator = OperatorUtils.buildOperator();
    OperatorAttributes attributes = OperatorUtils.buildOperatorAttributes();
    attributes.setOperator(operator);

    Operator savedOp = operatorRepository.save(operator);
    OperatorAttributes savedAttributes = attributesRepository.save(attributes);

    assertAll(
        () -> {
          assertNotNull(savedOp);
          assertNotNull(savedAttributes);
          assertEquals(operator, savedAttributes.getOperator());
          assertEquals(1, savedOp.getId());
          assertEquals(operator.getCodeName(), savedOp.getCodeName());
          assertEquals(operator.getSubclass(), savedOp.getSubclass());
        });
  }
}
