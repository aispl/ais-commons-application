package pl.ais.commons.application.feature;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

/**
 * Base class to be extended by {@link FeaturesHolder} implementations.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
@ThreadSafe
public class FeaturesHolderSupport implements FeaturesHolder {

    private final Map<Class<?>, Optional<?>> featuresMap;

    /**
     * Constructs new instance.
     *
     * @param featuresMap mapping of features owned by the holder
     */
    protected FeaturesHolderSupport(@Nonnull final Map<Class<?>, Optional<?>> featuresMap) {
        super();

        // Verify constructor requirements, ...
        if (null == featuresMap) {
            throw new IllegalArgumentException("Features map is required.");
        }

        // ... and initialize this instance fields.
        this.featuresMap = ImmutableMap.copyOf(featuresMap);
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    @SuppressWarnings("PMD.UselessParentheses")
    public boolean equals(final Object object) {
        boolean result = (this == object);
        if (!result && (null != object) && (getClass() == object.getClass())) {
            final FeaturesHolderSupport other = (FeaturesHolderSupport) object;
            result = Objects.equals(featuresMap, other.featuresMap);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <F> F getFeature(@Nonnull final Class<F> feature) throws UnsupportedFeatureException,
    VirtualFeatureException {

        // Verify method requirements, ...
        if (null == feature) {
            throw new IllegalArgumentException("Feature is required.");
        }

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
     * @return unmodifiable view of the features owned by the holder
     */
    protected Map<Class<?>, Optional<?>> getFeaturesMap() {
        return featuresMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasFeature(@Nonnull final Class<?> feature) {

        // Verify method requirements, ...
        if (null == feature) {
            throw new IllegalArgumentException("Feature is required.");
        }

        // ... and do the work.
        return featuresMap.containsKey(feature);
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(featuresMap);
    }

}
