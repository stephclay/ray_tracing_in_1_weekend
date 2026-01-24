package com.wombatsw.raytracing.scene;

import com.wombatsw.raytracing.scene.dto.DTO;
import com.wombatsw.raytracing.scene.dto.DTOType;
import com.wombatsw.raytracing.scene.dto.material.MaterialDTO;
import lombok.Getter;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * The resolution context for registering named {@link DTO}s and the looking then up when they are
 * referenced from a reference type
 */
@Getter
public class ResolveContext {
    private final Map<String, DTO<?>> triplets = new HashMap<>();
    private final Map<String, DTO<?>> textures = new HashMap<>();
    private final Map<String, DTO<?>> materials = new HashMap<>();

    /**
     * Register the provided map of named {@link DTO}s. The map may be null.
     *
     * @param dtoMap The map from name to {@link DTO}
     */
    public <T extends DTO<?>> void register(final Map<String, T> dtoMap) {
        if (dtoMap != null) {
            dtoMap.forEach(this::register);
        }
    }

    /**
     * Lookup a {@link DTO} by type and name
     *
     * @param type The type of {@link DTO}
     * @param name The name of the {@link DTO} to look up
     * @param <T>  The specific type of the {@link DTO}
     * @return The {@link DTO}
     * @throws IllegalArgumentException of the named value does not exist
     */
    public <T> T lookup(final DTOType type, final String name) {
        Map<String, DTO<?>> registry = getRegistry(type);

        @SuppressWarnings("unchecked")
        T value = (T) registry.get(name);

        if (value == null) {
            throw new IllegalArgumentException(String.format("%s with name %s not found.",
                    type.getDisplayName(), name));
        }

        return value;
    }

    /**
     * Register the provided {@link DTO} with a specific name
     *
     * @param name  The name of the {@link DTO}
     * @param value The {@link DTO}
     */
    private void register(final String name, final DTO<?> value) {
        Map<String, DTO<?>> registry = getRegistry(value.getType());
        registry.put(name, value);
    }

    /**
     * Get the {@link DTO} registry for the specified {@link DTOType}
     *
     * @param type The type of {@link DTO}
     * @return A {@link Map} from name to {@link DTO}
     */
    private Map<String, DTO<?>> getRegistry(final DTOType type) {
        return switch (type) {
            case MATERIAL -> materials;
            case TEXTURE -> textures;
            case TRIPLET -> triplets;
            default -> throw new IllegalArgumentException(String.format("Type %s not supported by registry.", type));
        };
    }
}
