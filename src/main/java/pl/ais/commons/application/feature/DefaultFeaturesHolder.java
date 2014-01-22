package pl.ais.commons.application.feature;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.google.common.base.Optional;

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
