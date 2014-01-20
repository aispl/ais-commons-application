package pl.ais.commons.application.feature;

import static com.google.common.base.Objects.toStringHelper;

import java.util.Map;

import javax.annotation.Nonnull;

import pl.ais.commons.application.util.Assert;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

/**
 * Default implementation of {@link FeaturesHolder}.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public class DefaultFeaturesHolder implements FeaturesHolder {

    private transient Map<Class<?>, Optional<?>> featuresMap;

    /**
     * Constructs new instance.
     *
     * @param featuresMap mapping of features owned by the holder
     */
    public DefaultFeaturesHolder(@Nonnull final Map<Class<?>, Optional<?>> featuresMap) {
        super();

        // Verify constructor requirements, ...
        Assert.notNull("Please, provide desired features map.", featuresMap);

        // ... and initialize this instance fields.
        this.featuresMap = ImmutableMap.copyOf(featuresMap);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <F> F getFeature(@Nonnull final Class<F> feature) throws UnsupportedFeatureException,
        VirtualFeatureException {

        // Verify method requirements, ...
        Assert.notNull("Please, provide the feature.", feature);

        // ... try to find feature handler, raise an exception if this feature is unsupported, ...
        final Optional<F> handler = (Optional<F>) featuresMap.get(feature);
        if (null == handler) {
            throw new UnsupportedFeatureException(feature);
        }

        // ... return the handler if present, raise an exception otherwise.
        if (handler.isPresent()) {
            return handler.get();
        }
        throw new VirtualFeatureException(feature);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasFeature(@Nonnull final Class<?> feature) {

        // Verify method requirements, ...
        Assert.notNull("Please, provide the feature.", feature);

        // ... and do the work.
        return featuresMap.containsKey(feature);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return toStringHelper(this).add("features", featuresMap.keySet()).toString();
    }

}
