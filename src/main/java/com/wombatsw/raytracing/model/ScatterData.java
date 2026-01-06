package com.wombatsw.raytracing.model;

/**
 * The results of scattering light off of a surface
 *
 * @param ray         The outbound ray
 * @param attenuation The attenuation color
 */
public record ScatterData(Ray ray, Color attenuation) {
}
