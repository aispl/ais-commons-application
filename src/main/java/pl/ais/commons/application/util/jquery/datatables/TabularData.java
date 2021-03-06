package pl.ais.commons.application.util.jquery.datatables;

import java.io.Serializable;

/**
 * Provides set of utility methods for adapting different data providers to {@link TabularDataProvider}.
 *
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
public final class TabularData {

    /**
     * Constructs new instance.
     */
    private TabularData() {
        super();
    }

    /**
     * Adapts {@link SearchResultsProvider} as {@link TabularDataProvider}.
     *
     * @param provider  search results provider
     * @param converter search results converter
     * @return adapted {@link SearchResultsProvider}
     */
    public static <E extends Serializable> SearchResultsAdapter<E> adaptSearchResults(
        final SearchResultsProvider<E> provider, final SearchResultsConverter<E> converter) {
        return new SearchResultsAdapter<>(provider, converter);
    }
}
