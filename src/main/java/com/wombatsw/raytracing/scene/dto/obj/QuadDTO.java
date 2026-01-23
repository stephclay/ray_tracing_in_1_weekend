package com.wombatsw.raytracing.scene.dto.obj;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wombatsw.raytracing.obj.Quad;
import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.scene.ref.MaterialRef;
import com.wombatsw.raytracing.scene.ref.TripletRef;
import lombok.ToString;

/**
 * A DTO for {@link Quad} objects
 */
@ToString(callSuper = true)
public class QuadDTO extends ObjectDTO<Quad> {
    private final TripletRef cornerRef;
    private final TripletRef v1Ref;
    private final TripletRef v2Ref;
    private final MaterialRef materialRef;

    public QuadDTO(@JsonProperty("corner") final TripletRef cornerRef,
                   @JsonProperty("v1") final TripletRef v1Ref,
                   @JsonProperty("v2") final TripletRef v2Ref,
                   @JsonProperty("material") final MaterialRef materialRef) {
        this.cornerRef = cornerRef;
        this.v1Ref = v1Ref;
        this.v2Ref = v2Ref;
        this.materialRef = materialRef;
    }

    @Override
    protected Quad createFromDTO(final ResolveContext context) {
        return new Quad(cornerRef.resolve(context),
                v1Ref.resolve(context),
                v2Ref.resolve(context),
                materialRef.resolve(context));
    }
}
