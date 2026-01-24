package com.wombatsw.raytracing.scene.dto.obj;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wombatsw.raytracing.obj.Quad;
import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.scene.dto.TripletDTO;
import com.wombatsw.raytracing.scene.dto.material.MaterialDTO;
import com.wombatsw.raytracing.scene.ref.MaterialRef;
import com.wombatsw.raytracing.scene.ref.TripletRef;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * A DTO for {@link Quad} objects
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"corner", "u", "v", "material"})
public class QuadDTO extends ObjectDTO<Quad> {
    private final TripletRef corner;
    private final TripletRef u;
    private final TripletRef v;
    private final MaterialRef material;

    public QuadDTO(@JsonProperty("corner") final TripletRef corner,
                   @JsonProperty("u") final TripletRef u,
                   @JsonProperty("v") final TripletRef v,
                   @JsonProperty("material") final MaterialRef material) {
        this.corner = corner;
        this.u = u;
        this.v = v;
        this.material = material;
    }

    public QuadDTO(final Quad quad) {
        this(new TripletRef(new TripletDTO(quad.getQ())),
                new TripletRef(new TripletDTO(quad.getU())),
                new TripletRef(new TripletDTO(quad.getV())),
                new MaterialRef(MaterialDTO.toDTO(quad.getMaterial())));
    }

    @Override
    protected Quad createFromDTO(final ResolveContext context) {
        return new Quad(corner.resolve(context),
                u.resolve(context),
                v.resolve(context),
                material.resolve(context));
    }
}
