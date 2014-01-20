package pl.ais.commons.application.feature.spring.internal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.ais.commons.application.feature.FeaturesHolder;

/**
 * Example controller.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@Controller
@RequestMapping("/example.html")
public class ExampleController {

    /**
     * Name of the Model attribute holding Features Holder.
     */
    public static final String FEATURES_HOLDER = "featuresHolder";

    /**
     * Responds to {@link RequestMethod#GET} requests.
     *
     * @param model the Model
     * @param featuresHolder features holder
     */
    @SuppressWarnings("static-method")
    @RequestMapping(method = RequestMethod.GET)
    public void doHandle(final Model model, final FeaturesHolder featuresHolder) {
        model.addAttribute(FEATURES_HOLDER, featuresHolder);
    }

}
