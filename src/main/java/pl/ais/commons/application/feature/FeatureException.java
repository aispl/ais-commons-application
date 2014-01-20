package pl.ais.commons.application.feature;

import java.io.IOException;
import java.io.ObjectInputStream;

import javax.annotation.Nonnull;

import pl.ais.commons.application.util.Assert;

/**
 * Base class to be extended by all feature related exceptions.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
public class FeatureException extends RuntimeException {

    /**
     * Identifies the original class version for which it is capable of writing streams and from which it can read.
     *
     * @see <a href="http://docs.oracle.com/javase/7/docs/platform/serialization/spec/version.html#6678">Type Changes Affecting Serialization</a>
     */
    private static final long serialVersionUID = -2330732137848728889L;

    private final Class<?> feature;

    /**
     * Constructs new instance.
     *
     * @param feature feature being reason of this exception
     */
    protected FeatureException(@Nonnull final Class<?> feature) {
        super();

        // Verify constructor requirements first, ...
        Assert.notNull("Please, provide the feature.", feature);

        // ... and initialize this instance fields.
        this.feature = feature;
    }

    /**
     * @return the feature being reason of this exception
     */
    public Class<?> getFeature() {
        return feature;
    }

    private void readObject(final ObjectInputStream objectStream) throws IOException, ClassNotFoundException {

        // Read object, ...
        objectStream.defaultReadObject();

        // ... and validate its state.
        validateState();
    }

    protected void validateState() {
        Assert.notNull("Please, provide the feature.", feature);
    }

}
