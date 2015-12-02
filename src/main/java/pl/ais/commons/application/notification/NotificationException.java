package pl.ais.commons.application.notification;

/**
 * Thrown to indicate that notification problem occurred.
 *
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
public final class NotificationException extends RuntimeException {

    private static final long serialVersionUID = -4282032864194424533L;

    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public NotificationException(final Throwable cause) {
        super(cause);
    }

}
