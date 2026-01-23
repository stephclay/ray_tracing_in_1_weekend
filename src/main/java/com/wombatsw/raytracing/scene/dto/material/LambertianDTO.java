package com.wombatsw.raytracing.scene.dto.material;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wombatsw.raytracing.material.Lambertian;
import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.scene.ref.TextureRef;
import com.wombatsw.raytracing.scene.ref.TripletRef;
import lombok.ToString;

/**
 * A DTO for {@link Lambertian} materials
 */
@ToString(callSuper = true)
public class LambertianDTO extends MaterialDTO<Lambertian> {
    private final TextureRef textureRef;
    private final TripletRef albedoRef;

    public LambertianDTO(@JsonProperty("texture") final TextureRef textureRef,
                         @JsonProperty("albedo") final TripletRef albedoRef) {
        this.textureRef = textureRef;
        this.albedoRef = albedoRef;
    }

    @Override
    protected Lambertian createFromDTO(ResolveContext context) {
        if (textureRef != null) {
            return new Lambertian(textureRef.resolve(context));
        } else if (albedoRef != null) {
            return new Lambertian(albedoRef.resolve(context));
        } else {
            throw new IllegalArgumentException("Lambertian requires either texture or albedo");
        }
    }
}
