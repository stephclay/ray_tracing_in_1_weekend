package com.wombatsw.raytracing.scene.ref;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.wombatsw.raytracing.model.Triplet;

import java.io.IOException;

/**
 * Special purpose deserializer to enable the Camera to be deserialized. If the camera is changed to a DTO,
 * then this will not be required.
 */
public class TripletDeserializer extends JsonDeserializer<Triplet> {
    @Override
    public Triplet deserialize(JsonParser p, DeserializationContext context) throws IOException {
        double[] v = p.readValueAs(double[].class);
        if (v.length != 3) {
            throw new IOException("Invalid number of elements for a triplet: " + v.length);
        }
        return new Triplet(v[0], v[1], v[2]);
    }
}
