package com.wombatsw.raytracing.scene.dto.material;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wombatsw.raytracing.material.Dielectric;
import com.wombatsw.raytracing.scene.ResolveContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * A DTO for {@link Dielectric} materials
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"refractionIndex"})
public class DielectricDTO extends MaterialDTO<Dielectric> {
    private final double refractionIndex;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public DielectricDTO(final double refractionIndex) {
        this.refractionIndex = refractionIndex;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public DielectricDTO(final int refractionIndex) {
        this((double) refractionIndex);
    }

    public DielectricDTO(final Dielectric material) {
        this(material.getRefractionIndex());
    }

    @Override
    protected Dielectric createFromDTO(ResolveContext context) {
        return new Dielectric(refractionIndex);
    }
}
