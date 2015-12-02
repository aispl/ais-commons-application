package pl.ais.commons.application.util.jquery.datatables;

import pl.ais.commons.query.SearchResults;

import java.util.function.Function;

/**
 * Defines the API contract for converting {@link SearchResults} into tabular data.
 *
 * @param <E> the type of each search result
 * @since 1.1.1
 */
public interface SearchResultsConverter<E> extends Function<SearchResults<E>, Object[][]> {

    // Empty by design ...

}