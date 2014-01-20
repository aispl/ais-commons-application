package pl.ais.commons.application.feature;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import pl.ais.commons.application.feature.internal.DefaultFeatureA;
import pl.ais.commons.application.feature.internal.FeatureA;
import pl.ais.commons.application.feature.internal.FeatureB;
import pl.ais.commons.application.feature.internal.FeatureC;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

/**
 * Verifies {@link FeaturesHolder} expectations.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
public class FeaturesHolderExpectations {

    /**
     * @return map holding Feature A, and virtual Feature B
     */
    private static Map<Class<?>, Optional<?>> featuresMap() {
        return ImmutableMap.<Class<?>, Optional<?>> builder().put(FeatureA.class, Optional.of(new DefaultFeatureA()))
            .put(FeatureB.class, Optional.absent()).build();
    }

    /**
     * Verifies if an exception is raised when unsupported feature is accessed.
     */
    @SuppressWarnings("static-method")
    @Test(expected = UnsupportedFeatureException.class)
    public void shouldRaiseExceptionOnUnsupportedFeatureAccess() {

        // Given FeaturesHolder with some set of features, ...
        final FeaturesHolderFactory factory = FeaturesHolderFactory.DEFAULT_FACTORY;
        final FeaturesHolder featuresHolder = factory.createFeaturesHolder(featuresMap());

        // ... when we access feature outside the set, ...
        featuresHolder.getFeature(FeatureC.class);

        // ... then it should raise a UnsupportedFeatureException.
    }

    /**
     * Verifies if an exception is raised when virtual feature is accessed.
     */
    @SuppressWarnings("static-method")
    @Test(expected = VirtualFeatureException.class)
    public void shouldRaiseExceptionOnVirtualFeatureAccess() {

        // Given FeaturesHolder with some set of features, ...
        final FeaturesHolderFactory factory = FeaturesHolderFactory.DEFAULT_FACTORY;
        final FeaturesHolder featuresHolder = factory.createFeaturesHolder(featuresMap());

        // ... when we access virtual feature, ...
        featuresHolder.getFeature(FeatureB.class);

        // ... then it should raise a VirtualFeatureException.
    }

    /**
     * Verifies if existing feature is correctly reported.
     */
    @SuppressWarnings("static-method")
    @Test
    public void shouldReportCorrectlyExistingFeature() {

        // Given FeaturesHolder with some set of features, ...
        final FeaturesHolderFactory factory = FeaturesHolderFactory.DEFAULT_FACTORY;
        final FeaturesHolder featuresHolder = factory.createFeaturesHolder(featuresMap());

        // ... when we ask about existence of feature from the set, ...
        final boolean result = featuresHolder.hasFeature(FeatureA.class);

        // ... then it should return true.
        assertTrue("Should return true for existing feature.", result);
    }

    /**
     * Verifies if non-existing feature is correctly reported.
     */
    @SuppressWarnings("static-method")
    @Test
    public void shouldReportCorrectlyNonExistingFeature() {

        // Given FeaturesHolder with some set of features, ...
        final FeaturesHolderFactory factory = FeaturesHolderFactory.DEFAULT_FACTORY;
        final FeaturesHolder featuresHolder = factory.createFeaturesHolder(featuresMap());

        // ... when we ask about existence of feature outside the set, ...
        final boolean result = featuresHolder.hasFeature(FeatureC.class);

        // ... then it should return false.
        assertFalse("Should return false for feature outside the set.", result);
    }

    /**
     * Verifies if existing, non-virtual feature is correctly returned.
     */
    @SuppressWarnings("static-method")
    @Test
    public void shouldReturnExistingFeature() {

        // Given FeaturesHolder with some set of features, ...
        final FeaturesHolderFactory factory = FeaturesHolderFactory.DEFAULT_FACTORY;
        final FeaturesHolder featuresHolder = factory.createFeaturesHolder(featuresMap());

        // ... when we request feature from the set, which is not virtual, ...
        final FeatureA feature = featuresHolder.getFeature(FeatureA.class);

        // ... then it should return the feature.
        assertNotNull("Should return feature.", feature);
    }
}
