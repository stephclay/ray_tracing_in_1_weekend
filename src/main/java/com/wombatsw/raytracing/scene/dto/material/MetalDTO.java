package com.wombatsw.raytracing.scene.dto.material;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wombatsw.raytracing.material.Metal;
import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.scene.dto.TripletDTO;
import com.wombatsw.raytracing.scene.ref.TripletRef;
import lombok.Getter;
import lombok.ToString;

/**
 * A DTO for {@link Metal} materials
 */
@Getter
@ToString(callSuper = true)
public class MetalDTO extends MaterialDTO<Metal> {
    private final TripletRef albedo;
    private final double fuzz;

    public MetalDTO(@JsonProperty("albedo") final TripletRef albedo,
                    @JsonProperty("fuzz") final double fuzz) {
        this.albedo = albedo;
        this.fuzz = fuzz;
    }

    public MetalDTO(final Metal metal) {
        this(new TripletRef(new TripletDTO(metal.getAlbedo())), metal.getFuzz());
    }

    @Override
    protected Metal createFromDTO(ResolveContext context) {
        if (albedo == null) {
            throw new IllegalArgumentException("Albedo is required");
        }

        return new Metal(albedo.resolve(context), fuzz);
    }
}
