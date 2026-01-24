package com.wombatsw.raytracing.scene.dto.material;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wombatsw.raytracing.material.DiffuseLight;
import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.scene.ref.TextureRef;
import com.wombatsw.raytracing.scene.ref.TripletRef;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * A DTO for {@link DiffuseLight} materials
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiffuseLightDTO extends MaterialDTO<DiffuseLight> {
    private final TextureRef texture;
    private final TripletRef color;

    public DiffuseLightDTO(@JsonProperty("texture") final TextureRef texture,
                           @JsonProperty("color") final TripletRef color) {
        this.texture = texture;
        this.color = color;
    }

    public DiffuseLightDTO(final DiffuseLight diffuseLight, final ResolveContext context) {
        this(context.getTextureRef(diffuseLight.getTexture()), null);
    }

    @Override
    protected DiffuseLight createFromDTO(ResolveContext context) {
        if (texture != null) {
            return new DiffuseLight(texture.resolve(context));
        } else if (color != null) {
            return new DiffuseLight(color.resolve(context));
        } else {
            throw new IllegalArgumentException("DiffuseLight requires either texture or color");
        }
    }
}
