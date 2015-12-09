package pl.ais.commons.application.util.property;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.Formatter;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;

/**
 * {@link PropertyEditor} delegating the work to {@link Formatter}.
 *
 * @param <T> determines the serviced type
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public class DelegatingPropertyEditor<T> extends PropertyEditorSupport {

    private final Formatter<T> formatter;

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
     * Adapts given {@link Formatter formatter} as {@link PropertyEditor property editor}.
     *
     * @param formatter the formatter to adapt
     * @return new {@link PropertyEditor property editor} instance backed by given formatter.
     */
    public static <T> PropertyEditor adapt(final Formatter<T> formatter) {
        return new DelegatingPropertyEditor<>(formatter);
    }

    /**
     * Returns {@link PropertyEditorFactory} creating {@link DelegatingPropertyEditor}
     * instances using given {@code formatter}.
     *
     * @param formatter the formatter which will be used by property editors created by the factory
     * @return {@link PropertyEditorFactory} creating {@link DelegatingPropertyEditor}
     * instances using given {@code formatter}
     * @since 1.0.2
     */
    public static <T> PropertyEditorFactory factoryFor(final Formatter<T> formatter) {
        return () -> new DelegatingPropertyEditor<>(formatter);
    }

    /**
     * @see java.beans.PropertyEditorSupport#getAsText()
     */
    @Override
    public String getAsText() {
        return formatter.print(getValue(), LocaleContextHolder.getLocale());
    }

    /**
     * @see java.beans.PropertyEditorSupport#getValue()
     */
    @SuppressWarnings("unchecked")
    @Override
    public T getValue() {
        return (T) super.getValue();
    }

    /**
     * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
     */
    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        try {
            setValue(formatter.parse(text, LocaleContextHolder.getLocale()));
        } catch (final ParseException exception) {
            throw new IllegalArgumentException(exception);
        }
    }
}
