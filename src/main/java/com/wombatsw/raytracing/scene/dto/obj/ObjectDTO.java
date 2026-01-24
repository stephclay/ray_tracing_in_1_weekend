package com.wombatsw.raytracing.scene.dto.obj;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.wombatsw.raytracing.obj.AbstractObj;
import com.wombatsw.raytracing.obj.ObjectList;
import com.wombatsw.raytracing.obj.Quad;
import com.wombatsw.raytracing.obj.Sphere;
import com.wombatsw.raytracing.obj.Transform;
import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.scene.dto.DTO;
import com.wombatsw.raytracing.scene.dto.DTOType;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A DTO for scene objects
 *
 * @param <T> The type of scene object
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.WRAPPER_OBJECT
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BoxDTO.class, name = "Box"),
        @JsonSubTypes.Type(value = ObjectListDTO.class, name = "ObjectList"),
        @JsonSubTypes.Type(value = QuadDTO.class, name = "Quad"),
        @JsonSubTypes.Type(value = SphereDTO.class, name = "Sphere"),
        @JsonSubTypes.Type(value = TransformDTO.class, name = "Transform")
})
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class ObjectDTO<T extends AbstractObj> extends DTO<T> {
    public ObjectDTO() {
        super(DTOType.OBJECT);
    }

    public static ObjectDTO<? extends AbstractObj> toDTO(AbstractObj value, final ResolveContext context) {
        if (value instanceof ObjectList obj) {
            return new ObjectListDTO(obj, context);
        }
        if (value instanceof Quad obj) {
            return new QuadDTO(obj, context);
        }
        if (value instanceof Sphere obj) {
            return new SphereDTO(obj, context);
        }
        if (value instanceof Transform obj) {
            return new TransformDTO(obj, context);
        }
        // Note: Box does not have its own object, it is a collection of quads. If that changes, add it here
        throw new IllegalArgumentException("Unknown object type: " + value.getClass().getSimpleName());
    }
}
