package com.arkbase.operator;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class OperatorRepositoryTest {

  @Autowired private EntityManager entityManager;

  @Autowired private OperatorRepository repository;

  @Test
  void shouldSaveNewOperator() {}
}
