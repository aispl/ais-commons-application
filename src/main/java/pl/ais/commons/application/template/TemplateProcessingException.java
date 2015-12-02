package pl.ais.commons.application.template;

/**
 * Thrown to indicate that there was template processing problem.
 *
 * @author Warlock
 * @since 1.2.1
 */
public final class TemplateProcessingException extends RuntimeException {

    private static final long serialVersionUID = -5850060667459243616L;

    /**
     * Constructs new instance.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public TemplateProcessingException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
