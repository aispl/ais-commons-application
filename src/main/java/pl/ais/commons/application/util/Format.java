package pl.ais.commons.application.util;

import org.springframework.format.Formatter;
import org.springframework.format.Printer;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * Provides set of utility methods for data formatting.
 *
 * @author Warlock
 * @since 1.2.1
 */
@Immutable
public final class Format {

    /**
     * Constructs new instance.
     */
    private Format() {
        super();
    }

    /**
     * Returns {@link Printer} implementation using <code>toString</code> method
     *
     * @param <T>
     * @return
     */
    public static <T> Printer<T> toStringPrinter() {
        return (object, locale) -> Objects.toString(object, null);
    }

    /**
     * @param parsingFunction  function converting from String to {@literal <T>} type
     * @param printingFunction function converting from {@literal <T>} type to String
     * @param <T>              the type of object formatted by returner Formatter
     * @return Formatter instance delegating the work to given {@code parsingFunction} and {@code printingFunction}
     */
    public static <T> Formatter<T> formatter(@Nullable final BiFunction<String, Locale, T> parsingFunction,
                                             @Nullable final BiFunction<T, Locale, String> printingFunction) {
        return new Formatter<T>() {

            @Override
            public T parse(final String text, final Locale locale) {
                return Optional.ofNullable(parsingFunction)
                               .orElseThrow(() -> new UnsupportedOperationException("Parsing function undefined."))
                               .apply(text, locale);
            }

            @Override
            public String print(final T object, final Locale locale) {
                return Optional.ofNullable(printingFunction)
                               .orElseThrow(() -> new UnsupportedOperationException("Printing function undefined."))
                               .apply(object, locale);
            }
        };
    }

}
