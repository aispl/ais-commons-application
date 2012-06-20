package pl.ais.commons.application.feature;

/**
 * Thrown to indicate that the requested feature is unsupported.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "PMD.MissingSerialVersionUID"})
public class UnsupportedFeatureException extends RuntimeException {

    private final Class<?> feature;

    /**
     * Constructs new instance.
     *
     * @param feature unsupported feature
     */
    public UnsupportedFeatureException(final Class<?> feature) {
        super();
        this.feature = feature;
    }

    /**
     * @return the feature
     */
    public Class<?> getFeature() {
        return feature;
    }

}
