package pl.ais.commons.application.util.jquery.datatables;

import pl.ais.commons.query.SearchResults;
import pl.ais.commons.query.SearchResultsProvider;
import pl.ais.commons.query.Selection;
import pl.ais.commons.query.SelectionFactory;
import pl.ais.commons.query.Selections;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.springframework.web.bind.ServletRequestUtils.getIntParameter;

/**
 * Adapts {@link SearchResultsProvider} as the {@link TabularDataProvider}.
 *
 * @param <E> the type of each search result
 * @author Warlock, AIS.PL
 * @since 1.1.1
 */
@Immutable
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public final class SearchResultsAdapter<E extends Serializable> implements TabularDataProvider {

    private final SearchResultsConverter<E> converter;

    private final SearchResultsProvider<E> provider;

    /**
     * Constructs new instance.
     *
     * @param provider  search results provider
     * @param converter search results converter
     */
    public SearchResultsAdapter(@Nonnull final SearchResultsProvider<E> provider,
                                @Nonnull final SearchResultsConverter<E> converter) {

        // Verify constructor requirements, ...
        Objects.requireNonNull(provider, "Provider is required");
        Objects.requireNonNull(converter, "Converter is required");

        // ... and initialize this instance fields.
        this.converter = converter;
        this.provider = provider;
    }

    protected static <R extends Serializable, S extends Selection<R>> Selection<R> createSelection(
        final HttpServletRequest request, final SelectionFactory<R, S> selectionFactory) {
        final int startIndex = getIntParameter(request, JQueryDataTables.DISPLAY_START, 0);
        final int displayLength = getIntParameter(request, JQueryDataTables.DISPLAY_LENGTH, -1);
        return null == selectionFactory ? Selections.<R>slice(startIndex, displayLength) : Selections.slice(
            startIndex, displayLength, selectionFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> inResponseTo(final HttpServletRequest request) {
        return inResponseTo(createSelection(request, null));
    }

    /**
     * Creates a Map holding all information required by jQuery DataTables for viewing the data.
     *
     * @param selection
     * @return a Map holding all information required by jQuery DataTables for viewing the data
     */
    @SuppressWarnings("boxing")
    protected <R extends Serializable> Map<String, Object> inResponseTo(final Selection<R> selection) {
        final SearchResults<E> results = provider.provideForSelection(selection);

        final Map<String, Object> result = new HashMap<>();
        result.put(JQueryDataTables.DATA, converter.apply(results));
        result.put(JQueryDataTables.TOTAL_NO, results.getTotalRecords());
        result.put(JQueryDataTables.FILTERED_NO, results.getTotalRecords());
        return result;
    }

    /**
     * Returns {@link TabularDataProvider} using given selection factory and orderings.
     *
     * @param selectionFactory selection factory
     * @param orderings        orderings to be used
     * @return {@link TabularDataProvider} instance
     */
    public <R extends Serializable, S extends Selection<R>> TabularDataProvider orderedWith(
        final SelectionFactory<R, S> selectionFactory, final R[][] orderings) {
        return new OrderedSearchResultsAdapter<>(selectionFactory, orderings);
    }

    /**
     * Adapts ordered results provided by {@link SearchResultsProvider}.
     *
     * @param <R> the type of ordering
     * @param <S> the type of selection
     */
    @Immutable
    private final class OrderedSearchResultsAdapter<R extends Serializable, S extends Selection<R>> implements
        TabularDataProvider {

        private final R[][] orderings;

        private final SelectionFactory<R, S> selectionFactory;

        /**
         * Constructs new instance.
         *
         * @param selectionFactory selection factory
         * @param orderings        data orderings
         */
        protected OrderedSearchResultsAdapter(final SelectionFactory<R, S> selectionFactory, final R[][] orderings) {
            this.orderings = Arrays.copyOf(orderings, orderings.length);
            this.selectionFactory = selectionFactory;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Map<String, Object> inResponseTo(final HttpServletRequest request) {
            Selection<R> selection = createSelection(request, selectionFactory);
            final int sortingColsNo = getIntParameter(request, JQueryDataTables.SORT_COLS_NO, 0);
            for (int i = 0; i < sortingColsNo; i++) {
                int index = 2 * Integer.parseInt(request.getParameter(JQueryDataTables.SORT_COL_PREFIX + i));
                if (JQueryDataTables.DESC.equalsIgnoreCase(request.getParameter(JQueryDataTables.SORT_DIR + i))) {
                    index++;
                }
                selection = selection.withOrderings(Arrays.asList(orderings[index]));
            }
            return SearchResultsAdapter.this.inResponseTo(selection);
        }

    }

}
