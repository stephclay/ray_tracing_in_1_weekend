package com.wombatsw.raytracing.scene.dto.obj;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wombatsw.raytracing.model.Affine;
import com.wombatsw.raytracing.obj.AbstractObj;
import com.wombatsw.raytracing.obj.Transform;
import com.wombatsw.raytracing.scene.ResolveContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * A DTO for {@link Transform} objects
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"object", "matrix"})
public class TransformDTO extends ObjectDTO<Transform> {
    private final ObjectDTO<?> object;
    private final String matrix;

    public TransformDTO(@JsonProperty("object") final ObjectDTO<?> object,
                        @JsonProperty("matrix") final String matrix) {
        this.object = object;
        this.matrix = matrix;
    }

    public TransformDTO(final Transform transform, final ResolveContext context) {
        this(ObjectDTO.toDTO(transform.getObject(), context), transform.getAffine().serialize());
    }

    @Override
    protected Transform createFromDTO(final ResolveContext context) {
        AbstractObj obj = (AbstractObj) object.resolve(context);
        return new Transform(obj, Affine.deserialize(matrix));
    }
}
