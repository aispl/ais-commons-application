package pl.ais.commons.application.feature;

import static com.google.common.base.Objects.toStringHelper;

import java.util.Map;

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
    public DefaultFeaturesHolder(final Map<Class<?>, Optional<?>> featuresMap) {
        super();
        this.featuresMap = ImmutableMap.copyOf(featuresMap);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <F> F getFeature(final Class<F> feature) throws UnsupportedFeatureException, VirtualFeatureException {
        final Optional<F> handler = (Optional<F>) featuresMap.get(feature);
        if (null == handler) {
            throw new UnsupportedFeatureException(feature);
        }
        if (handler.isPresent()) {
            return handler.get();
        }
        throw new VirtualFeatureException(feature);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasFeature(final Class<?> feature) {
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
