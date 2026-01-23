package com.wombatsw.raytracing.scene.ref;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.wombatsw.raytracing.scene.dto.texture.TextureDTO;
import com.wombatsw.raytracing.texture.Texture;

import java.io.IOException;

/**
 * Deserializer for {@link TextureRef}s
 */
public class TextureRefDeserializer extends JsonDeserializer<TextureRef> {
    @Override
    public TextureRef deserialize(JsonParser p, DeserializationContext context) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        if (node.isTextual()) {
            return new TextureRef(node.asText());
        }

        if (node.isObject()) {
            @SuppressWarnings("unchecked")
            TextureDTO<Texture> value = context.readTreeAsValue(node, TextureDTO.class);
            return new TextureRef(value);
        }

        throw new IllegalArgumentException("Texture must be an object or a reference");
    }
}
