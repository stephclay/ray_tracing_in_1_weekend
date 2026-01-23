package com.wombatsw.raytracing.scene.dto.texture;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.texture.NoiseTexture;
import lombok.ToString;

/**
 * A DTO for {@link NoiseTexture}
 */
@ToString(callSuper = true)
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

    @Override
    protected NoiseTexture createFromDTO(final ResolveContext context) {
        return new NoiseTexture(scale);
    }
}
