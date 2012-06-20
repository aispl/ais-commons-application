package pl.ais.commons.application.util.format;

import java.text.ParseException;
import java.util.Locale;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.format.Formatter;

/**
 * {@link Formatter} implementation applicable to {@link LocalDate}.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public class LocalDateFormatter implements Formatter<LocalDate> {

    /**
     * Singleton instance.
     */
    public static final LocalDateFormatter INSTANCE = new LocalDateFormatter();

    private static final String DEFAULT_FORMAT = "MM/dd/yyyy";

    private transient final DateTimeFormatter dateFormatter;

    /**
     * Constructs new instance using default date format (MM/dd/yyyy).
     */
    public LocalDateFormatter() {
        this(DEFAULT_FORMAT);
    }

    /**
     * Constructs new instance using specified date format.
     *
     * @param dateFormat the date format to use
     */
    public LocalDateFormatter(final String dateFormat) {
        super();
        this.dateFormatter = DateTimeFormat.forPattern(dateFormat);
    }

    /**
     * @see org.springframework.format.Parser#parse(java.lang.String, java.util.Locale)
     */
    @Override
    @SuppressWarnings("PMD.NullAssignment")
    public LocalDate parse(final String text, final Locale locale) throws ParseException {
        final DateTimeFormatter formatter = dateFormatter.withLocale(locale);
        return (null == text) || (0 == text.length()) ? null : LocalDate.parse(text, formatter);
    }

    /**
     * @see org.springframework.format.Printer#print(java.lang.Object, java.util.Locale)
     */
    @Override
    @SuppressWarnings("PMD.NullAssignment")
    public String print(final LocalDate object, final Locale locale) {
        final DateTimeFormatter formatter = dateFormatter.withLocale(locale);
        return (null == object) ? null : formatter.print(object);
    }

}
