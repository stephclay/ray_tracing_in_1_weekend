package com.wombatsw.raytracing.scene.ref;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.wombatsw.raytracing.scene.dto.TripletDTO;

import java.io.IOException;

/**
 * Deserializer for {@link TripletRef}s
 */
public class TripletRefDeserializer extends JsonDeserializer<TripletRef> {
    @Override
    public TripletRef deserialize(JsonParser p, DeserializationContext context) throws IOException {
        JsonNode node = p.getCodec().readTree(p);

        if (node.isTextual()) {
            return new TripletRef(node.asText());
        }

        if (node.isArray()) {
            TripletDTO value = context.readTreeAsValue(node, TripletDTO.class);
            return new TripletRef(value);
        }

        throw new IllegalArgumentException("Triplet must be an array or a reference");
    }
}
