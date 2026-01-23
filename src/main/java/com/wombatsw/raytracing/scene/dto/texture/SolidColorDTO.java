package com.wombatsw.raytracing.scene.dto.texture;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.scene.ref.TripletRef;
import com.wombatsw.raytracing.texture.SolidColor;
import lombok.ToString;

/**
 * A DTO for {@link SolidColor} textures
 */
@ToString(callSuper = true)
public class SolidColorDTO extends TextureDTO<SolidColor> {
    private final TripletRef tripletRef;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public SolidColorDTO(final TripletRef tripletRef) {
        this.tripletRef = tripletRef;
    }

    @Override
    protected SolidColor createFromDTO(ResolveContext context) {
        return new SolidColor(tripletRef.resolve(context));
    }
}
