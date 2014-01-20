package pl.ais.commons.application.feature;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Map;

import org.junit.Test;

import pl.ais.commons.application.feature.internal.BrokenFeaturesHolder;
import pl.ais.commons.application.feature.internal.FeatureA;
import pl.ais.commons.application.feature.internal.OperationalFeaturesHolder;
import pl.ais.commons.application.feature.internal.UnusableFeaturesHolder;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

/**
 * Verifies {@link FeaturesHolderFactory} expectations.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
public class FeaturesHolderFactoryExpectations {

    /**
     * @return empty features map
     */
    private static Map<Class<?>, Optional<?>> emptyFeaturesMap() {
        return ImmutableMap.<Class<?>, Optional<?>> of();
    }

    /**
     * Verifies if custom {@link FeaturesHolderFactory} creates requested type instances.
     */
    @SuppressWarnings("static-method")
    @Test
    public void customFactoryShouldCreateRequestedTypeInstances() {

        // Given custom FeaturesHolderFactory, ...
        final FeaturesHolderFactory factory = new FeaturesHolderFactory(OperationalFeaturesHolder.class);

        // ... when we create FeaturesHolder, ...
        final FeaturesHolder featuresHolder = factory.createFeaturesHolder(emptyFeaturesMap());

        // ... then created instance should be of type requested by us.
        assertThat("Custom FeaturesHolder factory should return requested type instances.", featuresHolder,
            instanceOf(OperationalFeaturesHolder.class));
    }

    /**
     * Verifies if default {@link FeaturesHolderFactory} creates {@link DefaultFeaturesHolder} instances.
     */
    @SuppressWarnings("static-method")
    @Test
    public void defaultFactoryShouldCreateDefaultFeaturesHolderInstances() {

        // Given default FeaturesHolderFactory, ...
        final FeaturesHolderFactory factory = FeaturesHolderFactory.getInstance();

        // ... when we create FeaturesHolder, ...
        final FeaturesHolder featuresHolder = factory.createFeaturesHolder(emptyFeaturesMap());

        // ... then created instance should be of type DefaultFeaturesHolder.
        assertThat("Default FeaturesHolder factory should return DefaultFeaturesHolder instances.", featuresHolder,
            instanceOf(DefaultFeaturesHolder.class));
    }

    /**
     * Verifies if {@link FeaturesHolderFactory} throws {@link IllegalStateException} when broken
     * {@link FeaturesHolder} type was requested.
     */
    @SuppressWarnings({"static-method", "unused"})
    @Test(expected = IllegalStateException.class)
    public void factoryShouldThrowExceptionWhenBrokenTypeRequested() {

        // Given broken FeaturesHolder type, ...
        final FeaturesHolderFactory factory = new FeaturesHolderFactory(BrokenFeaturesHolder.class);

        // ... when we create FeaturesHolder, ...
        final FeaturesHolder featuresHolder = factory.createFeaturesHolder(emptyFeaturesMap());

        // ... then exception will be thrown.
    }

    /**
     * Verifies if {@link FeaturesHolderFactory} throws {@link IllegalArgumentException} when unusable
     * {@link FeaturesHolder} type was requested.
     */
    @SuppressWarnings({"static-method", "unused"})
    @Test(expected = IllegalArgumentException.class)
    public void factoryShouldThrowExceptionWhenUnusableTypeRequested() {

        // Given unusable FeaturesHolder type, when we use it with FeaturesHolderFactory, ...
        new FeaturesHolderFactory(UnusableFeaturesHolder.class);

        // ... then exception will be thrown.
    }

    /**
     * Verifies if {@link FeaturesHolderFactory} is using given features for created instances.
     */
    @SuppressWarnings("static-method")
    @Test
    public void factoryShouldUseGivenFeaturesForCreatingInstances() {

        // Given FeaturesHolderFactory instance, ...
        final FeaturesHolderFactory factory = FeaturesHolderFactory.getInstance();

        // ... when we create FeaturesHolder with some feature, ...
        final Map<Class<?>, Optional<?>> featuresMap = ImmutableMap.<Class<?>, Optional<?>> builder()
            .put(FeatureA.class, Optional.absent()).build();
        final FeaturesHolder featuresHolder = factory.createFeaturesHolder(featuresMap);

        // ... then factory should use given features for created instances
        assertTrue("FeaturesHolder factory should use given features map for created instances.",
            featuresHolder.hasFeature(FeatureA.class));
    }

}
