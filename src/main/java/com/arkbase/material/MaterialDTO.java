package com.arkbase.material;

import lombok.Builder;

@Builder
public record MaterialDTO(Integer id, String name, Rarity rarity, String description) {}
