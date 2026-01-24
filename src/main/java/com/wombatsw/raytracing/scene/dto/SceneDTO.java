package com.wombatsw.raytracing.scene.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.wombatsw.raytracing.engine.Camera;
import com.wombatsw.raytracing.obj.AbstractObj;
import com.wombatsw.raytracing.obj.BVHNode;
import com.wombatsw.raytracing.obj.ObjectList;
import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.scene.Scene;
import com.wombatsw.raytracing.scene.SceneFile;
import com.wombatsw.raytracing.scene.dto.material.MaterialDTO;
import com.wombatsw.raytracing.scene.dto.obj.ObjectDTO;
import com.wombatsw.raytracing.scene.dto.texture.TextureDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The DTO for Scenes.
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"camera", "triplets", "textures", "materials", "world"})
public class SceneDTO extends DTO<Scene> {
    @Getter
    private final Camera camera;
    @Getter
    @JsonProperty("world")
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

        context.register(tripletDTOs, textureDTOs, materialDTOs);
    }

    public SceneDTO(final Scene scene) {
        super(DTOType.SCENE);

        this.camera = scene.getCamera();
        this.objectDTOList = new ArrayList<>();
        mapToDTO(this.objectDTOList, scene.getWorld());
    }

    @JsonProperty
    public Map<String, ? extends TripletDTO> getTriplets() {
        return context.getTriplets();
    }

    @JsonProperty
    public Map<String, ? extends TextureDTO<?>> getTextures() {
        return context.getTextures();
    }

    @JsonProperty
    public Map<String, ? extends MaterialDTO<?>> getMaterials() {
        return context.getMaterials();
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

    private void mapToDTO(List<ObjectDTO<?>> objectDTOList, AbstractObj object) {
        if (object instanceof ObjectList objList) {
            objList.getList()
                    .forEach(obj -> {
                        mapToDTO(objectDTOList, obj);
                    });
        } else if (object instanceof BVHNode bvhNode) {
            mapToDTO(objectDTOList, bvhNode.getLeft());
            if (bvhNode.getLeft() != bvhNode.getRight()) {
                mapToDTO(objectDTOList, bvhNode.getRight());
            }
        } else {
            objectDTOList.add(ObjectDTO.toDTO(object, context));
        }
    }
}
