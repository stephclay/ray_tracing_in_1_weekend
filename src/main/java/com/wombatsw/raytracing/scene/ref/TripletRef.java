package com.wombatsw.raytracing.scene.ref;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wombatsw.raytracing.model.Triplet;
import com.wombatsw.raytracing.scene.dto.DTOType;
import com.wombatsw.raytracing.scene.dto.TripletDTO;
import lombok.ToString;

/**
 * A {@link TripletDTO} reference
 */
@JsonDeserialize(using = TripletRefDeserializer.class)
@ToString(callSuper = true)
public class TripletRef extends Ref<Triplet> {
    public TripletRef(final String name) {
        super(name);
    }

    public TripletRef(final TripletDTO value) {
        super(value);
    }

    @Override
    protected DTOType getDTOType() {
        return DTOType.TRIPLET;
    }
}
