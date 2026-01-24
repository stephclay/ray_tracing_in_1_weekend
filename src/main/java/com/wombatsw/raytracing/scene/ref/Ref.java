package com.wombatsw.raytracing.scene.ref;

import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.scene.dto.DTO;
import com.wombatsw.raytracing.scene.dto.DTOType;
import lombok.Getter;
import lombok.ToString;

/**
 * A reference base class for scene files. Allows the file to have either a named reference or the full definition
 * of the DTO
 *
 * @param <T> The type of object that the DTO will be converted to
 */
@Getter
@ToString
public abstract class Ref<T> {
    private final String name;
    private final DTO<? extends T> value;

    /**
     * Construct a named reference
     *
     * @param name The name of the referenced value
     */
    public Ref(final String name) {
        this.name = name;
        this.value = null;
    }

    /**
     * Construct an inline reference
     *
     * @param value The DTO of the value being referenced
     */
    public Ref(final DTO<? extends T> value) {
        this.name = null;
        this.value = value;
    }

    /**
     * Get the type of DTO for this reference
     *
     * @return The {@link DTOType}
     */
    protected abstract DTOType getDTOType();

    /**
     * Resolve this reference into an actual object used in the Scene definition
     *
     * @param context The {@link ResolveContext}
     * @return The resolved object
     * @throws IllegalArgumentException if this is a named reference and there is no object associated with that name,
     *                                  or if the returned object cannot be constructed due to missing data
     */
    public T resolve(final ResolveContext context) {
        return resolveReference(context).resolve(context);
    }

    /**
     * Resolve the DTO associated with this reference
     *
     * @param context The {@link ResolveContext}
     * @return The DTO
     * @throws IllegalArgumentException if this is a named reference and there is no object associated with that name
     */
    private DTO<? extends T> resolveReference(final ResolveContext context) {
        if (value != null) {
            return value;
        }

        return context.lookup(getDTOType(), getName());
    }
}
