package pl.ais.commons.application.template.freemarker;

import freemarker.template.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Customizable FreeMarker configurer.
 *
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
public class CustomizableFreeMarkerConfigurer extends FreeMarkerConfigurer {

    private Map<String, String> autoImports = new LinkedHashMap<>();

    private int tagSyntax = Configuration.AUTO_DETECT_TAG_SYNTAX;

    @Override
    protected void postProcessConfiguration(final Configuration config) {
        autoImports.entrySet().forEach(entry -> config.addAutoImport(entry.getKey(), entry.getValue()));
        config.setTagSyntax(tagSyntax);
    }

    /**
     * @param autoImports the auto imports to set.
     */
    public void setAutoImports(@Nonnull final Map<String, String> autoImports) {
        Objects.requireNonNull(autoImports, "Auto-imports map is required.");
        this.autoImports = autoImports;
    }

    /**
     * @param tagSyntax the tag syntax to set
     */
    public void setTagSyntax(final int tagSyntax) {
        this.tagSyntax = tagSyntax;
    }

}
