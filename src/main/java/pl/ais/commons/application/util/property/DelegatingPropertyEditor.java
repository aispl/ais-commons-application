package pl.ais.commons.application.util.property;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.Formatter;

/**
 * {@link PropertyEditor} delegating the work to {@link Formatter}.
 *
 * @param <T> determines the serviced type
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public class DelegatingPropertyEditor<T> extends PropertyEditorSupport {

    /**
     * Adapts given {@link Formatter formatter} as {@link PropertyEditor property editor}.
     *
     * @param formatter the formatter to adapt
     * @return new {@link PropertyEditor property editor} instance backed by given formatter.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static PropertyEditor adapt(final Formatter<?> formatter) {
        return new DelegatingPropertyEditor(formatter);
    }

    private final transient Formatter<T> formatter;

    /**
     * Constructs new instance.
     *
     * @param formatter
     */
    public DelegatingPropertyEditor(final Formatter<T> formatter) {
        super();
        this.formatter = formatter;
    }

    /**
     * @see java.beans.PropertyEditorSupport#getAsText()
     */
    @Override
    public String getAsText() {
        return formatter.print((T) getValue(), LocaleContextHolder.getLocale());
    }

    /**
     * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
     */
    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        try {
            setValue(formatter.parse(text, LocaleContextHolder.getLocale()));
        } catch (ParseException exception) {
            throw new IllegalArgumentException(exception);
        }
    }
}
