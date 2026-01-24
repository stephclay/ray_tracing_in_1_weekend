package com.wombatsw.raytracing.scene.dto.obj;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wombatsw.raytracing.obj.AbstractObj;
import com.wombatsw.raytracing.obj.ObjectList;
import com.wombatsw.raytracing.scene.ResolveContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for {@link ObjectList} objects
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ObjectListDTO extends ObjectDTO<ObjectList> {
    private final List<? extends ObjectDTO<?>> objects;

    public ObjectListDTO(@JsonProperty("objects") final List<ObjectDTO<?>> objects) {
        this.objects = objects;
    }

    public ObjectListDTO(final ObjectList objectList, final ResolveContext context) {
        this.objects = objectList.getList().stream()
                .map(obj -> ObjectDTO.toDTO(obj, context))
                .toList();
    }

    @Override
    protected ObjectList createFromDTO(final ResolveContext context) {
        List<AbstractObj> objectList = new ArrayList<>(objects.size());
        objects.forEach(obj -> obj.resolve(context));

        return new ObjectList(objectList);
    }
}
