package com.arkbase.operator;

import com.arkbase.operator.enums.AttackType;
import com.arkbase.operator.enums.Position;
import com.arkbase.skill.SkillDTO;
import java.util.List;
import lombok.Builder;

@Builder
public record OperatorDTO(
    Integer id,
    String codeName,
    String archetype,
    String subclass,
    String trait,
    String rarity,
    Position position,
    AttackType attackType,
    OperatorAttributesDTO attributes,
    List<SkillDTO> skills) {}
