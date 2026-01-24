package com.wombatsw.raytracing.scene.ref;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
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

    @Override
    public void serializeWithType(Ref<?> value, JsonGenerator gen, SerializerProvider serializers,
                                  TypeSerializer typeSer) throws IOException {
        WritableTypeId typeId = typeSer.writeTypePrefix(gen,
                typeSer.typeId(value, JsonToken.VALUE_EMBEDDED_OBJECT));

        if (value.getName() != null) {
            gen.writeString(value.getName());
        } else {
            serializers.defaultSerializeValue(value.getValue(), gen);
        }

        typeSer.writeTypeSuffix(gen, typeId);
    }
}
