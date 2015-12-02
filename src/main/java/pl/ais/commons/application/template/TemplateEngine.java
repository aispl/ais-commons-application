package pl.ais.commons.application.template;

import java.io.IOException;
import java.util.Map;

/**
 * Defines the API contract for template engine.
 *
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
public interface TemplateEngine {

    byte[] renderTemplate(final String template, final Map<String, Object> model) throws IOException;

}
