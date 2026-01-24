package com.wombatsw.raytracing.scene.dto.obj;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wombatsw.raytracing.obj.Sphere;
import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.scene.dto.TripletDTO;
import com.wombatsw.raytracing.scene.dto.material.MaterialDTO;
import com.wombatsw.raytracing.scene.ref.MaterialRef;
import com.wombatsw.raytracing.scene.ref.TripletRef;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * A DTO for {@link Sphere} objects
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"center", "radius", "material"})
public class SphereDTO extends ObjectDTO<Sphere> {
    private final TripletRef center;
    private final double radius;
    private final MaterialRef material;

    public SphereDTO(@JsonProperty("center") final TripletRef center,
                     @JsonProperty("radius") final double radius,
                     @JsonProperty("material") final MaterialRef material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    public SphereDTO(final Sphere sphere, final ResolveContext context) {
        this(new TripletRef(new TripletDTO(sphere.getCenterPath().origin())),
                sphere.getRadius(),
                context.getMaterialRef(sphere.getMaterial()));
    }

    @Override
    protected Sphere createFromDTO(final ResolveContext context) {
        return new Sphere(
                center.resolve(context),
                radius,
                material.resolve(context));
    }
}
