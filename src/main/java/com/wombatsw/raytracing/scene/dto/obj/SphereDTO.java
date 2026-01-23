package com.wombatsw.raytracing.scene.dto.obj;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wombatsw.raytracing.obj.Sphere;
import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.scene.ref.MaterialRef;
import com.wombatsw.raytracing.scene.ref.TripletRef;
import lombok.ToString;

/**
 * A DTO for {@link Sphere} objects
 */
@ToString(callSuper = true)
public class SphereDTO extends ObjectDTO<Sphere> {
    private final TripletRef centerRef;
    private final double radius;
    private final MaterialRef materialRef;

    public SphereDTO(@JsonProperty("center") final TripletRef centerRef,
                     @JsonProperty("radius") final double radius,
                     @JsonProperty("material") final MaterialRef materialRef) {
        this.centerRef = centerRef;
        this.radius = radius;
        this.materialRef = materialRef;
    }

    @Override
    protected Sphere createFromDTO(final ResolveContext context) {
        return new Sphere(
                centerRef.resolve(context),
                radius,
                materialRef.resolve(context));
    }
}
