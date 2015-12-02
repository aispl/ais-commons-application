package pl.ais.commons.application.util.jquery.datatables;

import pl.ais.commons.query.SearchResults;
import pl.ais.commons.query.Selection;

import java.util.function.Function;

/**
 * Defines the API contract for providing {@link SearchResults} for the specific {@link Selection}.
 *
 * @param <E> the type of each search result
 * @author Warlock, AIS.PL
 * @since 1.2.1
 */
public interface SearchResultsProvider<E> extends Function<Selection, SearchResults<E>> {

    /**
     * Provides {@link SearchResults} for given selection.
     *
     * @param selection determines which of the matching users should be fetched and how they should be ordered
     * @return {@link SearchResults} determined by given selection
     */
    @Override
    SearchResults<E> apply(Selection selection);

}
