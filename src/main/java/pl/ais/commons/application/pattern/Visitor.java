package pl.ais.commons.application.pattern;

/**
 * Marker interface for the Visitor.
 *
 * @param <V> determines specific type of the Visitor
 *
 * @see <a href="http://en.wikipedia.org/wiki/Visitor_pattern">Visitor Pattern</a>
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
public interface Visitor<V extends Visitor<V>> {

    // Empty by design ...

}
