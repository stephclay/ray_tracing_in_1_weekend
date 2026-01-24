package com.wombatsw.raytracing.scene.dto.texture;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.texture.NoiseTexture;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * A DTO for {@link NoiseTexture}
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"scale"})
public class NoiseTextureDTO extends TextureDTO<NoiseTexture> {
    private final double scale;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public NoiseTextureDTO(final double scale) {
        this.scale = scale;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public NoiseTextureDTO(final int scale) {
        this((double) scale);
    }

    public NoiseTextureDTO(final NoiseTexture texture) {
        this(texture.getScale());
    }

    @Override
    protected NoiseTexture createFromDTO(final ResolveContext context) {
        return new NoiseTexture(scale);
    }
}
