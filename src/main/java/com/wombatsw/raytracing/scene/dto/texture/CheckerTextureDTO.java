package com.wombatsw.raytracing.scene.dto.texture;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wombatsw.raytracing.scene.ResolveContext;
import com.wombatsw.raytracing.scene.ref.TextureRef;
import com.wombatsw.raytracing.scene.ref.TripletRef;
import com.wombatsw.raytracing.texture.CheckerTexture;
import com.wombatsw.raytracing.texture.SolidColor;
import com.wombatsw.raytracing.texture.Texture;
import lombok.ToString;

/**
 * A DTO for {@link CheckerTexture}
 */
@ToString(callSuper = true)
public class CheckerTextureDTO extends TextureDTO<CheckerTexture> {
    private final double scale;
    private final TextureRef evenTextureRef;
    private final TextureRef oddTextureRef;
    private final TripletRef evenColorRef;
    private final TripletRef oddColorRef;

    public CheckerTextureDTO(@JsonProperty("scale") final double scale,
                             @JsonProperty("evenTexture") final TextureRef evenTextureRef,
                             @JsonProperty("oddTexture") final TextureRef oddTextureRef,
                             @JsonProperty("evenColor") final TripletRef evenColorRef,
                             @JsonProperty("oddColor") final TripletRef oddColorRef) {
        this.scale = scale;
        this.evenTextureRef = evenTextureRef;
        this.oddTextureRef = oddTextureRef;
        this.evenColorRef = evenColorRef;
        this.oddColorRef = oddColorRef;
    }

    @Override
    protected CheckerTexture createFromDTO(final ResolveContext context) {
        Texture even = getTexture(context,
                evenTextureRef, "evenTexture",
                evenColorRef, "evenColor");
        Texture odd = getTexture(context,
                oddTextureRef, "oddTexture",
                oddColorRef, "oddColor");
        return new CheckerTexture(scale, even, odd);
    }

    /**
     * Get the texture from either a {@link TextureRef} or a color in a {@link TripletRef}
     *
     * @param context     The resolution context for looking up named scene elements
     * @param textureRef  The {@link TextureRef}
     * @param textureName The parameter name for the texture
     * @param colorRef    The {@link TripletRef} for the color
     * @param colorName   The parameter name for the color
     * @return The texture
     * @throws IllegalArgumentException if either the even or odd texture is missing
     */
    private Texture getTexture(final ResolveContext context,
                               final TextureRef textureRef, final String textureName,
                               final TripletRef colorRef, final String colorName) {
        if (textureRef != null) {
            return textureRef.resolve(context);
        }
        if (colorRef != null) {
            return new SolidColor(colorRef.resolve(context));
        }
        throw new IllegalArgumentException(String.format("One of %s or %s is required.", textureName, colorName));
    }
}
