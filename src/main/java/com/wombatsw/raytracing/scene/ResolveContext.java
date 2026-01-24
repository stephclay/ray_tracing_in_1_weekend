package com.wombatsw.raytracing.scene;

import com.wombatsw.raytracing.material.Material;
import com.wombatsw.raytracing.model.Triplet;
import com.wombatsw.raytracing.scene.dto.DTO;
import com.wombatsw.raytracing.scene.dto.DTOType;
import com.wombatsw.raytracing.scene.dto.TripletDTO;
import com.wombatsw.raytracing.scene.dto.material.MaterialDTO;
import com.wombatsw.raytracing.scene.dto.texture.TextureDTO;
import com.wombatsw.raytracing.scene.ref.MaterialRef;
import com.wombatsw.raytracing.scene.ref.TextureRef;
import com.wombatsw.raytracing.scene.ref.TripletRef;
import com.wombatsw.raytracing.texture.Texture;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * The resolution context for registering named {@link DTO}s and the looking then up when they are
 * referenced from a reference type
 */
@Getter
public class ResolveContext {
    private final Map<String, TripletDTO> triplets = new TreeMap<>();
    private final Map<String, TextureDTO<?>> textures = new TreeMap<>();
    private final Map<String, MaterialDTO<?>> materials = new TreeMap<>();
    private final Map<TripletDTO, TripletRef> tripletRefMap = new HashMap<>();
    private final Map<TextureDTO<?>, TextureRef> textureRefMap = new HashMap<>();
    private final Map<MaterialDTO<?>, MaterialRef> materialRefMap = new HashMap<>();

    private int tripletIndex = 1;
    private int textureIndex = 1;
    private int materialIndex = 1;

    /**
     * Register the provided maps of named {@link DTO}s. The maps may be null.
     *
     * @param tripletDTOs  The map from name to {@link TripletDTO}
     * @param textureDTOs  The map from name to {@link TextureDTO}
     * @param materialDTOs The map from name to {@link MaterialDTO}
     */
    public void register(final Map<String, TripletDTO> tripletDTOs,
                         final Map<String, TextureDTO<?>> textureDTOs,
                         final Map<String, MaterialDTO<?>> materialDTOs) {
        if (tripletDTOs != null) {
            triplets.putAll(tripletDTOs);
        }
        if (textureDTOs != null) {
            textures.putAll(textureDTOs);
        }
        if (materialDTOs != null) {
            materials.putAll(materialDTOs);
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
        Map<String, ? extends DTO<?>> registry =switch (type) {
            case MATERIAL -> materials;
            case TEXTURE -> textures;
            case TRIPLET -> triplets;
            default -> throw new IllegalArgumentException(String.format("Type %s not supported by registry.", type));
        };

        @SuppressWarnings("unchecked")
        T value = (T) registry.get(name);

        if (value == null) {
            throw new IllegalArgumentException(String.format("%s with name %s not found.",
                    type.getDisplayName(), name));
        }

        return value;
    }

    /**
     * Return a named {@link TripletRef} for the provided triplet
     *
     * @param triplet The triplet
     * @return The {@link TripletRef}
     */
    public TripletRef getTripletRef(final Triplet triplet) {
        TripletDTO dto = new TripletDTO(triplet);

        return tripletRefMap.computeIfAbsent(dto, val -> {
            String refName = "triplet" + tripletIndex++;
            triplets.put(refName, val);
            return new TripletRef(refName);
        });
    }

    /**
     * Return a named {@link TextureRef} for the provided texture
     *
     * @param texture The texture
     * @return The {@link TextureRef}
     */
    public TextureRef getTextureRef(final Texture texture) {
        TextureDTO<?> dto = TextureDTO.toDTO(texture, this);

        return textureRefMap.computeIfAbsent(dto, val -> {
            String refName = "texture" + textureIndex++;
            textures.put(refName, val);
            return new TextureRef(refName);
        });
    }

    /**
     * Return a named {@link MaterialRef} for the provided material
     *
     * @param material The material
     * @return The {@link MaterialRef}
     */
    public MaterialRef getMaterialRef(final Material material) {
        MaterialDTO<?> dto = MaterialDTO.toDTO(material, this);

        return materialRefMap.computeIfAbsent(dto, val -> {
            String refName = "material" + materialIndex++;
            materials.put(refName, val);
            return new MaterialRef(refName);
        });
    }
}
