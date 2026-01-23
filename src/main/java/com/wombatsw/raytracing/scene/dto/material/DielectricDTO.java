package com.wombatsw.raytracing.scene.dto.material;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.wombatsw.raytracing.material.Dielectric;
import com.wombatsw.raytracing.scene.ResolveContext;
import lombok.ToString;

/**
 * A DTO for {@link Dielectric} materials
 */
@ToString(callSuper = true)
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

    @Override
    protected Dielectric createFromDTO(ResolveContext context) {
        return new Dielectric(refractionIndex);
    }
}
