package com.wombatsw.raytracing.scene.dto.texture;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.scene.dto.TripletDTO;
import com.wombatsw.raytracing.scene.ref.TripletRef;
import com.wombatsw.raytracing.texture.SolidColor;
import lombok.Getter;
import lombok.ToString;

/**
 * A DTO for {@link SolidColor} textures
 */
@Getter
@ToString(callSuper = true)
public class SolidColorDTO extends TextureDTO<SolidColor> {
    private final TripletRef triplet;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public SolidColorDTO(final TripletRef triplet) {
        this.triplet = triplet;
    }

    public SolidColorDTO(final SolidColor color) {
        this(new TripletRef(new TripletDTO(color.getColor())));
    }

    @Override
    protected SolidColor createFromDTO(ResolveContext context) {
        return new SolidColor(triplet.resolve(context));
    }
}
