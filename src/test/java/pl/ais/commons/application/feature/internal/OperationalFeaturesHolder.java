package pl.ais.commons.application.feature.internal;

import java.util.Map;

import javax.annotation.concurrent.Immutable;

import pl.ais.commons.application.feature.FeaturesHolderSupport;
import pl.ais.commons.application.feature.FeaturesHolder;

import com.google.common.base.Optional;

/**
 * Custom implementation of {@link FeaturesHolder}.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@Immutable
public final class OperationalFeaturesHolder extends FeaturesHolderSupport {

    /**
     * @param featuresMap mapping of the features owned by the holder
     */
    public OperationalFeaturesHolder(final Map<Class<?>, Optional<?>> featuresMap) {
        super(featuresMap);
    }

}
