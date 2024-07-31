package com.arkbase.operator;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Integer> {

  boolean existsByCodeNameIgnoreCase(String codeName);

  Optional<Operator> findByCodeNameIgnoreCase(String codeName);
}
