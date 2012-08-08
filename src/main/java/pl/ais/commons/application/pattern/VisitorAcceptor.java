package pl.ais.commons.application.pattern;

/**
 * Defines the API contract for Visitor Acceptor.
 *
 * @param <V> determines specific type of the Visitor
 *
 * @see <a href="http://en.wikipedia.org/wiki/Visitor_pattern">Visitor Pattern</a>
 *
 * @author Warlock, AIS.PL
 * @since 1.0.2
 */
public interface VisitorAcceptor<V extends Visitor<V>> {

    /**
     * Accepts given visitor.
     *
     * @param visitor the visitor to accept
     * @return value of type {@literal R} specific for concrete visitor implementation
     */
    <R> R accept(V visitor);

}
