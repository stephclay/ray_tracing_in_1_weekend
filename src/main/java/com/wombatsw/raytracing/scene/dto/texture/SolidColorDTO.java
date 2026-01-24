package com.wombatsw.raytracing.scene.dto.texture;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.scene.dto.TripletDTO;
import com.wombatsw.raytracing.scene.ref.TripletRef;
import com.wombatsw.raytracing.texture.SolidColor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * A DTO for {@link SolidColor} textures
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"triplet"})
public class SolidColorDTO extends TextureDTO<SolidColor> {
    @JsonValue
    private final TripletRef color;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public SolidColorDTO(final TripletRef color) {
        this.color = color;
    }

    public SolidColorDTO(final SolidColor color, final ResolveContext context) {
        this(new TripletRef(new TripletDTO(color.getColor())));
    }

    @Override
    protected SolidColor createFromDTO(final ResolveContext context) {
        return new SolidColor(color.resolve(context));
    }
}
