package pl.ais.commons.application.feature;

/**
 * Thrown to indicate that the requested feature is virtual.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@SuppressWarnings({"PMD.BeanMembersShouldSerialize", "PMD.MissingSerialVersionUID"})
public class VirtualFeatureException extends RuntimeException {

    private final Class<?> feature;

    /**
     * Constructs new instance.
     *
     * @param feature virtual feature
     */
    public VirtualFeatureException(final Class<?> feature) {
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
