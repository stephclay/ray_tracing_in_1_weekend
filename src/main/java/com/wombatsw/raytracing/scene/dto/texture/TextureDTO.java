package com.wombatsw.raytracing.scene.dto.texture;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.wombatsw.raytracing.scene.dto.DTO;
import com.wombatsw.raytracing.scene.dto.DTOType;
import com.wombatsw.raytracing.texture.CheckerTexture;
import com.wombatsw.raytracing.texture.ImageTexture;
import com.wombatsw.raytracing.texture.NoiseTexture;
import com.wombatsw.raytracing.texture.SolidColor;
import com.wombatsw.raytracing.texture.Texture;
import lombok.ToString;

/**
 * A DTO for {@link Texture}s
 *
 * @param <T> The specific texture
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CheckerTextureDTO.class, name = "Checker"),
        @JsonSubTypes.Type(value = ImageTextureDTO.class, name = "Image"),
        @JsonSubTypes.Type(value = NoiseTextureDTO.class, name = "Noise"),
        @JsonSubTypes.Type(value = SolidColorDTO.class, name = "SolidColor")
})
@ToString(callSuper = true)
public abstract class TextureDTO<T extends Texture> extends DTO<Texture> {
    public TextureDTO() {
        super(DTOType.TEXTURE);
    }

    public static TextureDTO<? extends Texture> toDTO(final Texture value) {
        if (value instanceof CheckerTexture texture) {
            return new CheckerTextureDTO(texture);
        }
        if (value instanceof ImageTexture texture) {
            return new ImageTextureDTO(texture);
        }
        if (value instanceof NoiseTexture texture) {
            return new NoiseTextureDTO(texture);
        }
        if (value instanceof SolidColor texture) {
            return new SolidColorDTO(texture);
        }
        throw new IllegalArgumentException("Unknown texture type: " + value.getClass().getSimpleName());
    }
}
