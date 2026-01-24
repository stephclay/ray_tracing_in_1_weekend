package com.wombatsw.raytracing.scene.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wombatsw.raytracing.model.Triplet;
import com.wombatsw.raytracing.scene.ResolveContext;
import lombok.Getter;
import lombok.ToString;

/**
 * A DTO fpr {@link Triplet}s
 */
@Getter
@ToString(callSuper = true)
public class TripletDTO extends DTO<Triplet> {
    private final double[] values;

    @JsonCreator
    public TripletDTO(double[] values) {
        super(DTOType.TRIPLET);
        this.values = values;
    }

    @JsonValue
    public double[] getValues() {
        return values;
    }

    public TripletDTO(Triplet triplet) {
        super(DTOType.TRIPLET);
        values = new double[]{
                triplet.getValue(0),
                triplet.getValue(1),
                triplet.getValue(2)
        };
    }

    @Override
    protected Triplet createFromDTO(ResolveContext context) {
        return new Triplet(values[0], values[1], values[2]);
    }
}
