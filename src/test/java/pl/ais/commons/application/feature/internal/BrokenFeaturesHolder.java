package pl.ais.commons.application.feature.internal;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import pl.ais.commons.application.feature.FeaturesHolder;
import pl.ais.commons.application.feature.UnsupportedFeatureException;
import pl.ais.commons.application.feature.VirtualFeatureException;

import com.google.common.base.Optional;

/**
 * Example of broken implementation of {@link FeaturesHolder}.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@Immutable
public final class BrokenFeaturesHolder implements FeaturesHolder {

    /**
     * @param featuresMap mapping of the features owned by the holder
     */
    public BrokenFeaturesHolder(final Map<Class<?>, Optional<?>> featuresMap) {
        throw new IllegalArgumentException();
    }

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
