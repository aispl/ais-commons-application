package pl.ais.commons.application.feature;

/**
 * Utility class for verifying assertions related to {@linkplain FeaturesHolder}.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.2
 */
public final class FeaturesHolderAssert {

    /**
     * Provides {@linkplain FeaturesHolderAssert} instance for given features holder.
     *
     * @param featuresHolder the features holder
     * @return {@linkplain FeaturesHolderAssert} instance usable for verifying assertions against given features holder
     */
    public static FeaturesHolderAssert then(final FeaturesHolder featuresHolder) {
        return new FeaturesHolderAssert(featuresHolder);
    }

    private transient final FeaturesHolder featuresHolder;

    /**
     * Constructs new instance.
     *
     * @param featuresHolder features holder
     */
    private FeaturesHolderAssert(final FeaturesHolder featuresHolder) {
        super();
        this.featuresHolder = featuresHolder;
    }

    /**
     * Verifies if enclosed features holder has given feature.
     *
     * @param feature the feature
     */
    public void shouldHaveFeature(final Class<?> feature) {
        if (!featuresHolder.hasFeature(feature)) {
            throw new AssertionError("Feature: " + feature + " is not available for " + featuresHolder);
        }
    }

}
