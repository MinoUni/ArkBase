package com.arkbase.validation;

import com.arkbase.annotation.ValueOfEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, String> {

  private Set<String> acceptedValues;

  @Override
  public void initialize(ValueOfEnum constraintAnnotation) {
    acceptedValues =
        Stream.of(constraintAnnotation.enumClass().getEnumConstants())
            .map(Enum::name)
            .collect(Collectors.toSet());
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }
    return acceptedValues.contains(value.toUpperCase());
  }
}
