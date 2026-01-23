package com.wombatsw.raytracing.scene.dto.material;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.scene.dto.DTO;
import com.wombatsw.raytracing.scene.dto.DTOType;
import lombok.ToString;

/**
 * A DTO for {@link Material}s
 *
 * @param <T> The specific material
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DielectricDTO.class, name = "Dielectric"),
        @JsonSubTypes.Type(value = DiffuseLightDTO.class, name = "DiffuseLight"),
        @JsonSubTypes.Type(value = LambertianDTO.class, name = "Lambertian"),
        @JsonSubTypes.Type(value = MetalDTO.class, name = "Metal")
})
@ToString(callSuper = true)
public abstract class MaterialDTO<T extends Material> extends DTO<T> {
    public MaterialDTO() {
        super(DTOType.MATERIAL);
    }
}
