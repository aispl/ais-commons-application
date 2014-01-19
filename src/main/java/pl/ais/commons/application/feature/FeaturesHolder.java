package pl.ais.commons.application.feature;

import javax.annotation.Nonnull;

/**
 * Defines the API contract for features holder.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public interface FeaturesHolder {

    /**
     * Retrieves the feature.
     *
     * @param <F> determines the type of feature to be retrieved
     * @param feature feature to be retrieved
     * @return requested feature
     * @throws UnsupportedFeatureException if requested feature is not owned by this holder
     * @throws VirtualFeatureException if requested feature is virtual (has no implementation)
     */
    @Nonnull
    <F> F getFeature(@Nonnull final Class<F> feature) throws UnsupportedFeatureException, VirtualFeatureException;

    /**
     * Verifies if requested feature is owned by the holder.
     *
     * @param feature feature to be verified
     * @return {@code true} if the feature is owned by the holder, {@code false} otherwise
     */
    boolean hasFeature(@Nonnull final Class<?> feature);

}
