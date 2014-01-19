package pl.ais.commons.application.feature.internal;

import java.util.Map;

import pl.ais.commons.application.feature.DefaultFeaturesHolder;
import pl.ais.commons.application.feature.FeaturesHolder;

import com.google.common.base.Optional;

/**
 * Custom implementation of {@link FeaturesHolder}.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
public class OperationalFeaturesHolder extends DefaultFeaturesHolder {

    /**
     * @param featuresMap mapping of the features owned by the holder
     */
    public OperationalFeaturesHolder(final Map<Class<?>, Optional<?>> featuresMap) {
        super(featuresMap);
    }

}
