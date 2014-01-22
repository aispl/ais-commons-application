package pl.ais.commons.application.feature.internal;

import java.util.Map;

import javax.annotation.concurrent.Immutable;

import pl.ais.commons.application.feature.FeaturesHolder;
import pl.ais.commons.application.feature.FeaturesHolderSupport;

import com.google.common.base.Optional;

/**
 * Example of broken implementation of {@link FeaturesHolder}.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@Immutable
public final class BrokenFeaturesHolder extends FeaturesHolderSupport {

    /**
     * @param featuresMap mapping of the features owned by the holder
     */
    public BrokenFeaturesHolder(final Map<Class<?>, Optional<?>> featuresMap) {
        super(featuresMap);
        throw new IllegalArgumentException();
    }

}
