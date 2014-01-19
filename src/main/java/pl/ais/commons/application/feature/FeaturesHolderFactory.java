package pl.ais.commons.application.feature;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.annotation.concurrent.Immutable;


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
public class FeaturesHolderFactory {

    /**
     * Shared instance of default {@link FeaturesHolder} factory.
     */
    public static final FeaturesHolderFactory DEFAULT_FACTORY = new FeaturesHolderFactory();

    private transient Constructor<? extends FeaturesHolder> constructor;

    /**
     * Constructs new factory creating {@link DefaultFeaturesHolder} instances.
     */
    public FeaturesHolderFactory() {
        this(DefaultFeaturesHolder.class);
    }

    /**
     * Constructs new factory creating instances of desired {@link FeaturesHolder} implementation.
     *
     * @param targetClass desired {@link FeaturesHolder} implementation
     * @throws IllegalArgumentException if desired {@link FeaturesHolder} implementation cannot be used by this factory
     */
    public FeaturesHolderFactory(final Class<? extends FeaturesHolder> targetClass) throws IllegalArgumentException {
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
    public FeaturesHolder createFeaturesHolder(final Map<Class<?>, Optional<?>> featuresMap) {
        try {
            return constructor.newInstance(featuresMap);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            throw new IllegalStateException("Unable to instantiate " + constructor.getDeclaringClass(), exception);
        }
    }

}
