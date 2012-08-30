package pl.ais.commons.application.util.property;

import java.beans.PropertyEditor;

/**
 * Defines the API contract for {@link PropertyEditor} factory.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.2
 */
public interface PropertyEditorFactory {

    /**
     * Returns new instance of {@link PropertyEditor}.
     *
     * @return newly created instance of {@link PropertyEditor}
     */
    PropertyEditor newPropertyEditor();

}
