package pl.ais.commons.application.template.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import pl.ais.commons.application.template.TemplateEngine;
import pl.ais.commons.application.template.TemplateProcessingException;

import javax.annotation.Nonnull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import static org.springframework.beans.factory.BeanFactoryUtils.beanOfTypeIncludingAncestors;

/**
 * FreeMarker based template engine implementation.
 *
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
public final class FreeMarkerTemplateEngine implements TemplateEngine {

    private final Configuration configuration;

    /**
     * Constructs new instance.
     *
     * @param configuration FreeMarker configuration to be used
     */
    public FreeMarkerTemplateEngine(final Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * Constructs new instance using FreeMarker configuration factory defined in given application context.
     *
     * @param context application context
     */
    public static FreeMarkerTemplateEngine buildUpon(final ApplicationContext context) {
        try {
            final FreeMarkerConfig configFactory = beanOfTypeIncludingAncestors(context, FreeMarkerConfig.class, true, false);
            return new FreeMarkerTemplateEngine(configFactory.getConfiguration());
        } catch (final NoSuchBeanDefinitionException ex) {
            throw new ApplicationContextException(
                "Must define a single FreeMarkerConfig bean in this web application context "
                    + "(may be inherited): FreeMarkerConfigurer is the usual implementation. "
                    + "This bean may be given any name.", ex);
        }
    }

    private byte[] renderTemplate(final Template template, final Map<String, Object> model) throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            template.process(model, new OutputStreamWriter(outputStream, configuration.getDefaultEncoding()));
        } catch (final TemplateException exception) {
            throw new TemplateProcessingException("Exception caught while rendering template '" + template.getName()
                + "'", exception);
        }
        return outputStream.toByteArray();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] renderTemplate(@Nonnull final String templateName, @Nonnull final Map<String, Object> model) throws IOException {
        final Template template = configuration.getTemplate(templateName);
        return renderTemplate(template, model);
    }

}
