package pl.ais.commons.application.util.jquery.datatables;

import java.io.Serializable;

import pl.ais.commons.query.SearchResults;

import com.google.common.base.Function;

/**
 * Defines the API contract for converting {@link SearchResults} into tabular data.
 *
 * @param <E> the type of each search result
 * @since 1.1.1
 */
public interface SearchResultsConverter<E extends Serializable> extends Function<SearchResults<E>, Object[][]> {

    // Empty by design ...

}