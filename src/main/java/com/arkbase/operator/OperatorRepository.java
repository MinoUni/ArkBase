package com.arkbase.operator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Integer> {

    boolean existsByCodeName(String codeName);
}
