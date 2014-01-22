package pl.ais.commons.application.feature.spring;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.springframework.context.ApplicationContext;

import pl.ais.commons.application.feature.FeaturesHolder;
import pl.ais.commons.application.feature.FeaturesHolderFactory;

import com.google.common.base.Optional;

/**
 * Utility class for building instances of {@link FeaturesHolder}.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
final class FeaturesHolderBuilder {

    private final ApplicationContext context;

    private final FeaturesHolderFactory factory;

    private final Map<Class<?>, Optional<?>> featuresMap;

    /**
     * Constructs builder instance.
     *
     * <p>
     *   Constructs builder instance using provided {@link FeaturesHolderFactory} as a delegate for creating
     *   {@link FeaturesHolder} instances.
     * </p>
     *
     * @param factory factory capable of creating {@link FeaturesHolder} instances
     * @param context application context
     */
    public FeaturesHolderBuilder(@Nonnull final FeaturesHolderFactory factory, @Nonnull final ApplicationContext context) {
        super();
        this.context = context;
        this.factory = factory;
        this.featuresMap = new LinkedHashMap<>();
    }

    /**
     * Adds the feature to the {@link FeaturesHolder} being build currently.
     *
     * @param feature the feature to add
     * @return this builder instance (for method execution chaining)
     */
    public FeaturesHolderBuilder addFeature(@Nonnull final Class<?> feature) {

        // Verify method requirements, ...
        if (null == feature) {
            throw new IllegalArgumentException("Feature is required.");
        }

        // ... check if the feature wasn't added yet, ...
        if (!featuresMap.containsKey(feature)) {

            // ... find Spring Framework beans having feature type, ...
            final Map<String, ?> handlersMap = context.getBeansOfType(feature);
            if (handlersMap.isEmpty()) {

                // ... if there are no such beans, the feature will be considered as virtual, ...
                featuresMap.put(feature, Optional.absent());
            } else {

                // ... register the feature (using first bean from the map returned by Spring).
                featuresMap.put(feature, Optional.of(handlersMap.values().iterator().next()));
            }
        }
        return this;
    }

    /**
     * @return assembled instance of {@link FeaturesHolder}
     */
    @Nonnull
    public FeaturesHolder build() {
        return factory.createFeaturesHolder(featuresMap);
    }

}
