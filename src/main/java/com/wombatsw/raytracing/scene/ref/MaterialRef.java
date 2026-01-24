package com.wombatsw.raytracing.scene.ref;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.scene.dto.DTOType;
import com.wombatsw.raytracing.scene.dto.material.MaterialDTO;
import lombok.ToString;

/**
 * A {@link MaterialDTO} reference
 */
@JsonDeserialize(using = MaterialRefDeserializer.class)
@ToString(callSuper = true)
public class MaterialRef extends Ref<Material> {

    public MaterialRef(final String name) {
        super(name);
    }

    public MaterialRef(final MaterialDTO<? extends Material> value) {
        super(value);
    }

    @Override
    protected DTOType getDTOType() {
        return DTOType.MATERIAL;
    }
}
