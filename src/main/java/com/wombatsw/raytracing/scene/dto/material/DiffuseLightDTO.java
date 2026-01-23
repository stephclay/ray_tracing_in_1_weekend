package com.wombatsw.raytracing.scene.dto.material;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wombatsw.raytracing.material.DiffuseLight;
import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.scene.ref.TextureRef;
import com.wombatsw.raytracing.scene.ref.TripletRef;
import lombok.ToString;

/**
 * A DTO for {@link DiffuseLight} materials
 */
@ToString(callSuper = true)
public class DiffuseLightDTO extends MaterialDTO<DiffuseLight> {
    private final TextureRef textureRef;
    private final TripletRef colorRef;

    public DiffuseLightDTO(@JsonProperty("texture") final TextureRef textureRef,
                           @JsonProperty("color") final TripletRef colorRef) {
        this.textureRef = textureRef;
        this.colorRef = colorRef;
    }

    @Override
    protected DiffuseLight createFromDTO(ResolveContext context) {
        if (textureRef != null) {
            return new DiffuseLight(textureRef.resolve(context));
        } else if (colorRef != null) {
            return new DiffuseLight(colorRef.resolve(context));
        } else {
            throw new IllegalArgumentException("DiffuseLight requires either texture or color");
        }
    }
}
