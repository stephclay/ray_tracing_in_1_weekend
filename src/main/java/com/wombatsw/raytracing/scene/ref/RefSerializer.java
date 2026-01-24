package com.wombatsw.raytracing.scene.ref;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Deserializer for {@link Ref}s
 */
public class RefSerializer extends StdSerializer<Ref<?>> {
    @SuppressWarnings("unchecked")
    public RefSerializer() {
        super((Class<Ref<?>>) (Class<?>) Ref.class);
    }

    @Override
    public void serialize(Ref value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value.getName() != null) {
            gen.writeString(value.getName());
        } else if (value.getValue() != null) {
            gen.writeObject(value.getValue());
        } else {
            throw new IllegalArgumentException("Reference must contain a name or a value");
        }
    }
}
