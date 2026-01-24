package com.wombatsw.raytracing.scene.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wombatsw.raytracing.scene.ResolveContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * A Data transfer object for converting a scene definition file into a scene element
 *
 * @param <T> The data type that this DTO converts into
 */
@ToString
@EqualsAndHashCode
public abstract class DTO<T> {
    /**
     * The type of DTO. Used for looking up references in the registry
     */
    @Getter
    @JsonIgnore
    private final DTOType type;

    private T resolvedValue;

    public DTO(final DTOType type) {
        this.type = type;
    }

    /**
     * Resolve this DTO into a scene element. Will cache the result so that it does not have to be recreated
     * with each call.
     *
     * @param context The resolution context for looking up named scene elements
     * @return The resolved value
     * @throws IllegalArgumentException if the object cannot be constructed due to missing data
     */
    public T resolve(ResolveContext context) {
        if (resolvedValue == null) {
            resolvedValue = createFromDTO(context);
        }
        return resolvedValue;
    }

    /**
     * Create the scene element from this DTO. This will create a new object each time, but the resolve method
     * will cache the first result.
     *
     * @param context The resolution context for looking up named scene elements
     * @return The new scene element
     * @throws IllegalArgumentException if the object cannot be constructed due to missing data
     */
    protected abstract T createFromDTO(ResolveContext context);
}
