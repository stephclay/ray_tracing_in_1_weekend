package com.wombatsw.raytracing.scene.dto.texture;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.texture.ImageTexture;
import lombok.Getter;
import lombok.ToString;

/**
 * A DTO for {@link ImageTexture}
 */
@Getter
@ToString(callSuper = true)
public class ImageTextureDTO extends TextureDTO<ImageTexture> {
    private final String filename;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public ImageTextureDTO(final String filename) {
        this.filename = filename;
    }

    public ImageTextureDTO(final ImageTexture texture) {
        this(texture.getFilename());
    }

    @Override
    protected ImageTexture createFromDTO(final ResolveContext context) {
        return new ImageTexture(filename);
    }
}
