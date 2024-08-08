package com.arkbase.operator;

import com.arkbase.operator.enums.Archetype;
import com.arkbase.operator.enums.AttackType;
import com.arkbase.operator.enums.Position;
import com.arkbase.operator.enums.Subclass;
import com.arkbase.operator.enums.Trait;
import com.arkbase.skill.NewSkillDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Builder;

@Builder
public record NewOperatorDTO(
    @NotBlank String codeName,
    @NotNull Archetype archetype,
    @NotNull Subclass subclass,
    @NotNull Rarity rarity,
    @NotNull Trait trait,
    @NotNull Position position,
    @NotNull AttackType attackType,
    @Valid OperatorAttributesDTO attributes,
    @Valid @Size(max = 3) Set<NewSkillDTO> skills) {}
