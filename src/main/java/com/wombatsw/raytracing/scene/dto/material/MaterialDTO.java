package com.wombatsw.raytracing.scene.dto.material;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.wombatsw.raytracing.material.Dielectric;
import com.wombatsw.raytracing.material.DiffuseLight;
import com.wombatsw.raytracing.material.Lambertian;
import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.material.Metal;
import com.wombatsw.raytracing.scene.dto.DTO;
import com.wombatsw.raytracing.scene.dto.DTOType;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
public abstract class MaterialDTO<T extends Material> extends DTO<T> {
    public MaterialDTO() {
        super(DTOType.MATERIAL);
    }

    public static MaterialDTO<? extends Material> toDTO(final Material value) {
        if (value instanceof Dielectric material) {
            return new DielectricDTO(material);
        }
        if (value instanceof DiffuseLight material) {
            return new DiffuseLightDTO(material);
        }
        if (value instanceof Lambertian material) {
            return new LambertianDTO(material);
        }
        if (value instanceof Metal material) {
            return new MetalDTO(material);
        }
        throw new IllegalArgumentException("Unknown material type: " + value.getClass().getSimpleName());
    }
}
