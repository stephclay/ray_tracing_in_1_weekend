package com.wombatsw.raytracing.scene.ref;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wombatsw.raytracing.scene.dto.DTOType;
import com.wombatsw.raytracing.scene.dto.texture.TextureDTO;
import com.wombatsw.raytracing.texture.Texture;
import lombok.ToString;

/**
 * A {@link TextureDTO} reference
 */
@JsonDeserialize(using = TextureRefDeserializer.class)
@ToString(callSuper = true)
public class TextureRef extends Ref<Texture> {

    public TextureRef(final String name) {
        super(name);
    }

    public TextureRef(final TextureDTO<Texture> value) {
        super(value);
    }

    @Override
    protected DTOType getDTOType() {
        return DTOType.TEXTURE;
    }
}
