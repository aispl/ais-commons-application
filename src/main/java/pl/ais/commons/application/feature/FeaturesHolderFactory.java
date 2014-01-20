package pl.ais.commons.application.feature;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import pl.ais.commons.application.util.Assert;

import com.google.common.base.Optional;

/**
 * Customizable {@link FeaturesHolder} factory.
 *
 * <p>
 *   This factory is capable of creating either {@link DefaultFeaturesHolder} instances, or any other
 *   {@link FeaturesHolder} implementing class (as long as it has 1-arg constructor accepting Map of features).
 * </p>
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@Immutable
public final class FeaturesHolderFactory {

    /**
     * Shared instance of default {@link FeaturesHolder} factory.
     */
    private static final FeaturesHolderFactory DEFAULT_FACTORY = new FeaturesHolderFactory();

    /**
     * Returns shared instance of default {@link FeaturesHolder} factory.
     *
     * @return shared instance of default {@link FeaturesHolder} factory.
     */
    @Nonnull
    public static FeaturesHolderFactory getInstance() {
        return DEFAULT_FACTORY;
    }

    private transient Constructor<? extends FeaturesHolder> constructor;

    /**
     * Constructs new factory creating {@link DefaultFeaturesHolder} instances.
     */
    private FeaturesHolderFactory() {
        this(DefaultFeaturesHolder.class);
    }

    /**
     * Constructs new factory creating instances of desired {@link FeaturesHolder} implementation.
     *
     * @param targetClass desired {@link FeaturesHolder} implementation
     * @throws IllegalArgumentException if desired {@link FeaturesHolder} implementation cannot be used by this factory
     */
    public FeaturesHolderFactory(@Nonnull final Class<? extends FeaturesHolder> targetClass)
        throws IllegalArgumentException {

        // Verify constructor requirements, ...
        Assert.notNull("Please, provide the target class.", targetClass);

        // ... and do the work.
        try {
            this.constructor = targetClass.getConstructor(Map.class);
        } catch (NoSuchMethodException exception) {
            throw new IllegalArgumentException("Desired type (" + targetClass
                + ") doesn't have 1-arg constructor with Map parameter.", exception);
        }
    }

    /**
     * Creates new {@link FeaturesHolder} instance.
     *
     * @param featuresMap features which will be owned by created {@link FeaturesHolder}
     * @return newly created {@link FeaturesHolder} instance
     */
    @Nonnull
    public FeaturesHolder createFeaturesHolder(@Nonnull final Map<Class<?>, Optional<?>> featuresMap) {

        // Verify method requirements, ...
        Assert.notNull("Please, provide desird features map.", featuresMap);

        // ... and do the work.
        try {
            return constructor.newInstance(featuresMap);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            throw new IllegalStateException("Unable to instantiate " + constructor.getDeclaringClass(), exception);
        }
    }

}
