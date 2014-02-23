package pl.ais.commons.application.format.joda.time;

import java.text.ParseException;
import java.util.Locale;

import javax.annotation.concurrent.Immutable;

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
@Immutable
public final class LocalDateFormatter implements Formatter<LocalDate> {

    private static final String DEFAULT_FORMAT = "MM/dd/yyyy";

    /**
     * Singleton instance, using default 'MM/dd/yyyy' format.
     */
    public static final LocalDateFormatter INSTANCE = new LocalDateFormatter();

    private transient final DateTimeFormatter dateFormatter;

    /**
     * Constructs new instance using default date format (MM/dd/yyyy).
     */
    private LocalDateFormatter() {
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
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD.NullAssignment")
    public LocalDate parse(final String text, final Locale locale) throws ParseException {
        final DateTimeFormatter formatter = dateFormatter.withLocale(locale);
        return (null == text) || (0 == text.length()) ? null : LocalDate.parse(text, formatter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD.NullAssignment")
    public String print(final LocalDate object, final Locale locale) {
        final DateTimeFormatter formatter = dateFormatter.withLocale(locale);
        return (null == object) ? null : formatter.print(object);
    }

}
