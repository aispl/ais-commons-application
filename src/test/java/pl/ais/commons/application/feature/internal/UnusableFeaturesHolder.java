package pl.ais.commons.application.feature.internal;

import javax.annotation.Nonnull;

import pl.ais.commons.application.feature.FeaturesHolder;
import pl.ais.commons.application.feature.UnsupportedFeatureException;
import pl.ais.commons.application.feature.VirtualFeatureException;

/**
 * Example of unusable {@link FeaturesHolder} implementation.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
public class UnusableFeaturesHolder implements FeaturesHolder {

    /**
     * {@inheritDoc}
     */
    @Override
    @Nonnull
    public <F> F getFeature(@Nonnull final Class<F> feature) throws UnsupportedFeatureException,
        VirtualFeatureException {
        throw new UnsupportedFeatureException(feature);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasFeature(@Nonnull final Class<?> feature) {
        return false;
    }

}
