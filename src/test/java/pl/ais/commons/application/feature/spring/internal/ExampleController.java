package pl.ais.commons.application.feature.spring.internal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.ais.commons.application.feature.FeaturesHolder;
import pl.ais.commons.application.feature.internal.OperationalFeaturesHolder;

/**
 * Example controller.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@Controller
public class ExampleController {

    /**
     * Name of the Model attribute holding Features Holder.
     */
    public static final String FEATURES_HOLDER = "featuresHolder";

    /**
     * Example of method using default {@link FeaturesHolder} implementation.
     *
     * @param model the Model
     * @param featuresHolder features holder
     */
    @SuppressWarnings("static-method")
    @RequestMapping(method = RequestMethod.GET, value = "/default.html")
    public void doHandleDefault(final Model model, final FeaturesHolder featuresHolder) {
        model.addAttribute(FEATURES_HOLDER, featuresHolder);
    }

    /**
     * Example of method using specialized {@link FeaturesHolder} type.
     *
     * @param model the Model
     * @param featuresHolder features holder
     */
    @SuppressWarnings("static-method")
    @RequestMapping(method = RequestMethod.GET, value = "/operational.html")
    public void doHandleOperational(final Model model, final OperationalFeaturesHolder featuresHolder) {
        model.addAttribute(FEATURES_HOLDER, featuresHolder);
    }

}
