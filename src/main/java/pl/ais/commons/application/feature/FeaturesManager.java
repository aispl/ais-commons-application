package pl.ais.commons.application.feature;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Features manager.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "PMD.MissingSerialVersionUID"})
public class FeaturesManager implements FeaturesHolder, Serializable {

    /**
     * Retrieves the feature.
     *
     * @param holder features holder
     * @param feature feature request
     * @return the requested feature
     * @throws UnsupportedFeatureException if the requested feature is not owned by this holder,
     *         including the situation when holder is {@code null}
     * @throws VirtualFeatureException if the requested feature is virtual (has no implementation)
     */
    @Nonnull
    public static <T> T getFeatureFromHolder(@Nullable final FeaturesHolder holder, @Nonnull final Class<T> feature)
        throws UnsupportedFeatureException, VirtualFeatureException {
        if (null == holder) {
            throw new UnsupportedFeatureException(feature);
        }
        return holder.getFeature(feature);
    }

    private transient final Map<Class<?>, Object> featureHandlers = new HashMap<Class<?>, Object>();

    private transient final Set<Class<?>> features = new HashSet<Class<?>>();

    /**
     * @see pl.ais.commons.application.feature.FeaturesHolder#addFeature(java.lang.Class, java.lang.Object)
     */
    @Override
    public void addFeature(final Class<?> feature, final Object handler) {
        features.add(feature);
        featureHandlers.put(feature, handler);
    }

    /**
     * @see pl.ais.commons.application.feature.FeaturesHolder#addVirtualFeature(java.lang.Class)
     */
    @Override
    public void addVirtualFeature(final Class<?> feature) {
        features.add(feature);
    }

    /**
     * @see pl.ais.commons.application.feature.FeaturesHolder#getFeature(java.lang.Class)
     */
    @Override
    public <T> T getFeature(final Class<T> feature) throws UnsupportedFeatureException, VirtualFeatureException {
        if (!hasFeature(feature)) {
            throw new UnsupportedFeatureException(feature);
        }
        final T result = (T) featureHandlers.get(feature);
        if (null == result) {
            throw new VirtualFeatureException(feature);
        }
        return result;
    }

    /**
     * @see pl.ais.commons.application.feature.FeaturesHolder#hasFeature(java.lang.Class)
     */
    @Override
    public boolean hasFeature(final Class<?> feature) {
        return features.contains(feature);
    }

    /**
     * @see pl.ais.commons.application.feature.FeaturesHolder#removeFeature(java.lang.Class)
     */
    @Override
    public void removeFeature(final Class<?> feature) {
        features.remove(feature);
        featureHandlers.remove(feature);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        for (Class<?> feature : features) {
            builder.append(feature);
        }
        return builder.build();
    }

}
