package com.wombatsw.raytracing.scene.dto.obj;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wombatsw.raytracing.obj.AbstractObj;
import com.wombatsw.raytracing.obj.Quad;
import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.scene.ref.MaterialRef;
import com.wombatsw.raytracing.scene.ref.TripletRef;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * A DTO for axis-aligned box objects
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"corner1", "corner2", "material"})
public class BoxDTO extends ObjectDTO<AbstractObj> {
    private final TripletRef corner1;
    private final TripletRef corner2;
    private final MaterialRef material;

    public BoxDTO(@JsonProperty("corner1") final TripletRef corner1,
                  @JsonProperty("corner2") final TripletRef corner2,
                  @JsonProperty("material") final MaterialRef material) {
        this.corner1 = corner1;
        this.corner2 = corner2;
        this.material = material;
    }

    @Override
    protected AbstractObj createFromDTO(final ResolveContext context) {
        return Quad.createBox(corner1.resolve(context),
                corner2.resolve(context),
                material.resolve(context));
    }
}
