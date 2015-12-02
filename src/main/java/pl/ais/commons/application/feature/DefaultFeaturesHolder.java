package pl.ais.commons.application.feature;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.util.Map;
import java.util.Optional;

/**
 * Default {@link FeaturesHolder} implementation.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@Immutable
public final class DefaultFeaturesHolder extends FeaturesHolderSupport {

    /**
     * Constructs new instance.
     *
     * @param featuresMap mapping of features owned by the holder
     */
    public DefaultFeaturesHolder(@Nonnull final Map<Class<?>, Optional<?>> featuresMap) {
        super(featuresMap);
    }

}
