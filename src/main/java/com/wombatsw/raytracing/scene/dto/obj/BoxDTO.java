package com.wombatsw.raytracing.scene.dto.obj;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wombatsw.raytracing.obj.AbstractObj;
import com.wombatsw.raytracing.obj.Quad;
import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.scene.ref.MaterialRef;
import com.wombatsw.raytracing.scene.ref.TripletRef;
import lombok.ToString;

/**
 * A DTO for axis-aligned box objects
 */
@ToString(callSuper = true)
public class BoxDTO extends ObjectDTO<AbstractObj> {
    private final TripletRef cornerRef1;
    private final TripletRef cornerRef2;
    private final MaterialRef materialRef;

    public BoxDTO(@JsonProperty("corner1") final TripletRef cornerRef1,
                  @JsonProperty("corner2") final TripletRef cornerRef2,
                  @JsonProperty("material") final MaterialRef materialRef) {
        this.cornerRef1 = cornerRef1;
        this.cornerRef2 = cornerRef2;
        this.materialRef = materialRef;
    }

    @Override
    protected AbstractObj createFromDTO(final ResolveContext context) {
        return Quad.createBox(cornerRef1.resolve(context),
                cornerRef2.resolve(context),
                materialRef.resolve(context));
    }
}
