package com.arkbase.operator;

import com.arkbase.attribute.OperatorAttributes;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface OperatorAttributesRepository extends JpaRepository<OperatorAttributes, Integer> {}
