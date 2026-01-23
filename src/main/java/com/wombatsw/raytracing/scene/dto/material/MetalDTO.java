package com.wombatsw.raytracing.scene.dto.material;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wombatsw.raytracing.material.Metal;
import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.scene.ref.TripletRef;
import lombok.ToString;

/**
 * A DTO for {@link Metal} materials
 */
@ToString(callSuper = true)
public class MetalDTO extends MaterialDTO<Metal> {
    private final TripletRef albedoRef;
    private final double fuzz;

    public MetalDTO(@JsonProperty("albedo") final TripletRef albedoRef,
                    @JsonProperty("fuzz") final double fuzz) {
        this.albedoRef = albedoRef;
        this.fuzz = fuzz;
    }

    @Override
    protected Metal createFromDTO(ResolveContext context) {
        if (albedoRef == null) {
            throw new IllegalArgumentException("Albedo is required");
        }

        return new Metal(albedoRef.resolve(context), fuzz);
    }
}
