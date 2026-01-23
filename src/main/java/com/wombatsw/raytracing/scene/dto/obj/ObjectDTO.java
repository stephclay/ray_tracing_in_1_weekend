package com.wombatsw.raytracing.scene.dto.obj;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.wombatsw.raytracing.obj.AbstractObj;
import com.wombatsw.raytracing.scene.dto.DTO;
import com.wombatsw.raytracing.scene.dto.DTOType;
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
        @JsonSubTypes.Type(value = QuadDTO.class, name = "Quad"),
        @JsonSubTypes.Type(value = SphereDTO.class, name = "Sphere")
})
@ToString(callSuper = true)
public abstract class ObjectDTO<T extends AbstractObj> extends DTO<T> {
    public ObjectDTO() {
        super(DTOType.OBJECT);
    }
}
