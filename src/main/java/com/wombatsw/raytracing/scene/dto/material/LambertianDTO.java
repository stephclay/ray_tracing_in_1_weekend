package com.wombatsw.raytracing.scene.dto.material;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wombatsw.raytracing.material.Lambertian;
import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.scene.dto.TripletDTO;
import com.wombatsw.raytracing.scene.ref.TextureRef;
import com.wombatsw.raytracing.scene.ref.TripletRef;
import com.wombatsw.raytracing.texture.SolidColor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * A DTO for {@link Lambertian} materials
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LambertianDTO extends MaterialDTO<Lambertian> {
    private final TextureRef texture;
    private final TripletRef albedo;

    public LambertianDTO(@JsonProperty("texture") final TextureRef texture,
                         @JsonProperty("albedo") final TripletRef albedo) {
        this.texture = texture;
        this.albedo = albedo;
    }

    public LambertianDTO(final Lambertian lambertian, final ResolveContext context) {
        // TODO: Add the remaining simplifications to similar DTOs
        if (lambertian.getTexture() instanceof SolidColor solidColor) {
            this.texture = null;
            this.albedo = new TripletRef(new TripletDTO(solidColor.getColor()));
        }
        else {
            this.texture = context.getTextureRef(lambertian.getTexture());
            this.albedo = null;
        }
    }

    @Override
    protected Lambertian createFromDTO(ResolveContext context) {
        if (texture != null) {
            return new Lambertian(texture.resolve(context));
        } else if (albedo != null) {
            return new Lambertian(albedo.resolve(context));
        } else {
            throw new IllegalArgumentException("Lambertian requires either texture or albedo");
        }
    }
}
