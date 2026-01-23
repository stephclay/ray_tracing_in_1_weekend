package com.wombatsw.raytracing.scene.dto.texture;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.wombatsw.raytracing.scene.dto.DTO;
import com.wombatsw.raytracing.scene.dto.DTOType;
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
        @JsonSubTypes.Type(value = SolidColorDTO.class, name = "SolidColor")
})
@ToString(callSuper = true)
public abstract class TextureDTO<T extends Texture> extends DTO<Texture> {
    public TextureDTO() {
        super(DTOType.TEXTURE);
    }
}
