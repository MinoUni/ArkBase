package com.arkbase.operator;

import com.arkbase.operator.enums.AttackType;
import com.arkbase.operator.enums.Position;
import lombok.Builder;

@Builder
public record OperatorDetailsDTO(
    int id,
    String codeName,
    String archetype,
    String subclass,
    String trait,
    String rarity,
    Position position,
    AttackType attackType,
    OperatorAttributesDTO attributes) {}
