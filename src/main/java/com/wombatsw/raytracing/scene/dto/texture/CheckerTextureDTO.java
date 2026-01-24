package com.wombatsw.raytracing.scene.dto.texture;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.scene.ref.TextureRef;
import com.wombatsw.raytracing.scene.ref.TripletRef;
import com.wombatsw.raytracing.texture.CheckerTexture;
import com.wombatsw.raytracing.texture.SolidColor;
import com.wombatsw.raytracing.texture.Texture;
import lombok.Getter;
import lombok.ToString;

/**
 * A DTO for {@link CheckerTexture}
 */
@Getter
@ToString(callSuper = true)
public class CheckerTextureDTO extends TextureDTO<CheckerTexture> {
    private final double scale;
    private final TextureRef evenTexture;
    private final TextureRef oddTexture;
    private final TripletRef evenColor;
    private final TripletRef oddColor;

    public CheckerTextureDTO(@JsonProperty("scale") final double scale,
                             @JsonProperty("evenTexture") final TextureRef evenTexture,
                             @JsonProperty("oddTexture") final TextureRef oddTexture,
                             @JsonProperty("evenColor") final TripletRef evenColor,
                             @JsonProperty("oddColor") final TripletRef oddColor) {
        this.scale = scale;
        this.evenTexture = evenTexture;
        this.oddTexture = oddTexture;
        this.evenColor = evenColor;
        this.oddColor = oddColor;
    }

    public CheckerTextureDTO(final CheckerTexture texture) {
        this(texture.getScale(),
                new TextureRef(TextureDTO.toDTO(texture.getEven())),
                new TextureRef(TextureDTO.toDTO(texture.getOdd())),
                null, null);
    }

    @Override
    protected CheckerTexture createFromDTO(final ResolveContext context) {
        Texture even = getTexture(context,
                evenTexture, "evenTexture",
                evenColor, "evenColor");
        Texture odd = getTexture(context,
                oddTexture, "oddTexture",
                oddColor, "oddColor");
        return new CheckerTexture(scale, even, odd);
    }

    /**
     * Get the texture from either a {@link TextureRef} or a color in a {@link TripletRef}
     *
     * @param context     The resolution context for looking up named scene elements
     * @param texture     The {@link TextureRef}
     * @param textureName The parameter name for the texture
     * @param color       The {@link TripletRef} for the color
     * @param colorName   The parameter name for the color
     * @return The texture
     * @throws IllegalArgumentException if either the even or odd texture is missing
     */
    private Texture getTexture(final ResolveContext context,
                               final TextureRef texture, final String textureName,
                               final TripletRef color, final String colorName) {
        if (texture != null) {
            return texture.resolve(context);
        }
        if (color != null) {
            return new SolidColor(color.resolve(context));
        }
        throw new IllegalArgumentException(String.format("One of %s or %s is required.", textureName, colorName));
    }
}
