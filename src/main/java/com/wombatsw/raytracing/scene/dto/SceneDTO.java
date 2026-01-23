package com.wombatsw.raytracing.scene.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wombatsw.raytracing.engine.Camera;
import com.wombatsw.raytracing.obj.AbstractObj;
import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.scene.Scene;
import com.wombatsw.raytracing.scene.SceneFile;
import com.wombatsw.raytracing.scene.dto.material.MaterialDTO;
import com.wombatsw.raytracing.scene.dto.obj.ObjectDTO;
import com.wombatsw.raytracing.scene.dto.texture.TextureDTO;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The DTO for Scenes.
 */
@ToString(callSuper = true)
public class SceneDTO extends DTO<Scene> {
    @Getter
    private final Camera camera;
    private final Map<String, TripletDTO> tripletDTOs;
    private final Map<String, TextureDTO<?>> textureDTOs;
    private final Map<String, MaterialDTO<?>> materialDTOs;
    private final List<ObjectDTO<?>> world;

    public SceneDTO(@JsonProperty("camera") final Camera camera,
                    @JsonProperty("triplets") final Map<String, TripletDTO> tripletDTOs,
                    @JsonProperty("textures") final Map<String, TextureDTO<?>> textureDTOs,
                    @JsonProperty("materials") final Map<String, MaterialDTO<?>> materialDTOs,
                    @JsonProperty("world") final List<ObjectDTO<?>> world) {
        super(DTOType.SCENE);

        this.camera = camera == null ? new Camera() : camera;
        this.tripletDTOs = ensureMapExists(tripletDTOs);
        this.textureDTOs = ensureMapExists(textureDTOs);
        this.materialDTOs = ensureMapExists(materialDTOs);
        this.world = world;
    }

    /**
     * Create a new map if the provided one is {@code null}
     *
     * @param map The map which might be null
     * @param <T> The type of object that the map references
     * @return The provide map, or a new one if the provided one was {@code null}
     */
    private static <T> Map<String, T> ensureMapExists(Map<String, T> map) {
        return map == null ? new HashMap<>() : map;
    }

    @Override
    protected Scene createFromDTO(ResolveContext context) {
        List<AbstractObj> world = resolveWorldElements(context);
        return new SceneFile(camera, world);
    }

    /**
     * Resolve all the objects, materials, textures, etc.
     *
     * @param context The resolution context for looking up named scene elements
     * @return The list of scene objects
     */
    private List<AbstractObj> resolveWorldElements(ResolveContext context) {
        tripletDTOs.forEach(context::register);
        textureDTOs.forEach(context::register);
        materialDTOs.forEach(context::register);

        return world.stream()
                .map(dto -> (AbstractObj) dto.resolve(context))
                .toList();
    }
}
