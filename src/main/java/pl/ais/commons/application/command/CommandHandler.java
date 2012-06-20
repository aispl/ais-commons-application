package pl.ais.commons.application.command;

/**
 * Defines the API contract for command handler.
 *
 * @param <C> the command type
 * @param <R> the command execution result type (can be {@link Void})
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public interface CommandHandler<C extends Command, R> {

    /**
     * Handles the command.
     *
     * @param command the command to handle
     * @return the command execution result
     */
    R handle(C command);

}
