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

import java.util.List;
import java.util.Map;

/**
 * The DTO for Scenes.
 */
@ToString(callSuper = true)
public class SceneDTO extends DTO<Scene> {
    @Getter
    private final Camera camera;
    private final List<ObjectDTO<?>> objectDTOList;
    private final ResolveContext context = new ResolveContext();

    public SceneDTO(@JsonProperty("camera") final Camera camera,
                    @JsonProperty("triplets") final Map<String, TripletDTO> tripletDTOs,
                    @JsonProperty("textures") final Map<String, TextureDTO<?>> textureDTOs,
                    @JsonProperty("materials") final Map<String, MaterialDTO<?>> materialDTOs,
                    @JsonProperty("world") final List<ObjectDTO<?>> world) {
        super(DTOType.SCENE);

        this.camera = camera == null ? new Camera() : camera;
        this.objectDTOList = world;

        context.register(tripletDTOs);
        context.register(textureDTOs);
        context.register(materialDTOs);
    }

    /**
     * Convert this DTO into a {@link Scene}
     *
     * @return The {@link Scene}
     * @throws IllegalArgumentException if the scene be constructed due to missing data
     */
    public Scene toScene() {
        return resolve(context);
    }

    @Override
    protected Scene createFromDTO(ResolveContext context) {
        List<AbstractObj> world = objectDTOList.stream()
                .map(dto -> (AbstractObj) dto.resolve(context))
                .toList();

        return new SceneFile(camera, world);
    }
}
