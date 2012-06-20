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
     * Adds new feature.
     *
     * @param feature the feature to add
     * @param handler the feature handler
     */
    void addFeature(@Nonnull final Class<?> feature, @Nonnull final Object handler);

    /**
     * Adds new virtual feature.
     *
     * @param feature the feature to add
     */
    void addVirtualFeature(@Nonnull final Class<?> feature);

    /**
     * Retrieves the feature.
     *
     * @param feature feature request
     * @return the requested feature
     * @throws UnsupportedFeatureException if the requested feature is not owned by this holder
     * @throws VirtualFeatureException if the requested feature is virtual (has no implementation)
     */
    @Nonnull
    <T> T getFeature(@Nonnull final Class<T> feature) throws UnsupportedFeatureException, VirtualFeatureException;

    /**
     * Verifies if requested feature is owned by the holder.
     *
     * @param feature feature request
     * @return {@code true} if the feature is owned by the holder, {@code false} otherwise
     */
    boolean hasFeature(@Nonnull final Class<?> feature);

    /**
     * Removes the feature.
     *
     * @param feature the feature to remove
     */
    void removeFeature(@Nonnull final Class<?> feature);

}
