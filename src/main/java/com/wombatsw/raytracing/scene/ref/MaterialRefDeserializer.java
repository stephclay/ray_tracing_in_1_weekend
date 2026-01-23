package com.wombatsw.raytracing.scene.ref;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.scene.dto.material.MaterialDTO;

import java.io.IOException;

/**
 * Deserializer for {@link MaterialRef}s
 */
public class MaterialRefDeserializer extends JsonDeserializer<MaterialRef> {
    @Override
    public MaterialRef deserialize(JsonParser p, DeserializationContext context) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        if (node.isTextual()) {
            return new MaterialRef(node.asText());
        }

        if (node.isObject()) {
            @SuppressWarnings("unchecked")
            MaterialDTO<Material> value = context.readTreeAsValue(node, MaterialDTO.class);
            return new MaterialRef(value);
        }

        throw new IllegalArgumentException("Material must be an object or a reference");
    }
}
