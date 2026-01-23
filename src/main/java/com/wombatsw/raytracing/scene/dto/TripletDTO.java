package com.wombatsw.raytracing.scene.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.wombatsw.raytracing.model.Triplet;
import com.wombatsw.raytracing.scene.ResolveContext;
import lombok.ToString;

/**
 * A DTO fpr {@link Triplet}s
 */
@ToString(callSuper = true)
public class TripletDTO extends DTO<Triplet> {
    private final double[] values;

    @JsonCreator
    public TripletDTO(double[] values) {
        super(DTOType.TRIPLET);
        this.values = values;
    }

    @Override
    protected Triplet createFromDTO(ResolveContext context) {
        return new Triplet(values[0], values[1], values[2]);
    }
}
