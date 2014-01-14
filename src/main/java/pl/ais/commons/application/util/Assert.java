package pl.ais.commons.application.util;

/**
 * Utility class providing few basic assertions.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
public final class Assert {

    /**
     * Assert that an object is not {@code null}.
     *
     * @param object the object to check
     * @param message the message used for creating exception if the assertion fails
     */
    public static void notNull(final Object object, final String message) {
        if (null == object) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Constructs new instance.
     */
    private Assert() {
        super();
    }
}
