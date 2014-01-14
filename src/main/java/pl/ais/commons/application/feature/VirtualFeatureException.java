package pl.ais.commons.application.feature;

import javax.annotation.Nonnull;

/**
 * Thrown to indicate that the requested feature is virtual (has no implementation).
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public final class VirtualFeatureException extends FeatureException {

    /**
     * Identifies the original class version for which it is capable of writing streams and from which it can read.
     *
     * @see <a href="http://docs.oracle.com/javase/7/docs/platform/serialization/spec/version.html#6678">Type Changes Affecting Serialization</a>
     */
    private static final long serialVersionUID = -8967019754185308327L;

    /**
     * Constructs new instance.
     *
     * @param feature virtual feature
     */
    public VirtualFeatureException(@Nonnull final Class<?> feature) {
        super(feature);
    }

}
