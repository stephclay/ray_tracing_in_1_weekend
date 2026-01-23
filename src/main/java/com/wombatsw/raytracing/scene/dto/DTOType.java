package com.wombatsw.raytracing.scene.dto;

import lombok.Getter;
import org.springframework.util.StringUtils;

/**
 * The type of DTO, used for registry lookups when converting DTOs to scene elements
 */
public enum DTOType {
    SCENE,
    MATERIAL,
    OBJECT,
    TEXTURE,
    TRIPLET;

    @Getter
    private final String displayName;

    DTOType() {
        this.displayName = StringUtils.capitalize(name().toLowerCase());
    }
}
