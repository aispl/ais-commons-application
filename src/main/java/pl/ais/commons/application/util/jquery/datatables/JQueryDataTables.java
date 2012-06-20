package pl.ais.commons.application.util.jquery.datatables;

/**
 * Defines constants and shared methods useful for jQuery DataTables back-end.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public final class JQueryDataTables {

    /**
     * Name of the JSON result property holding the data which should be viewed in DataTables.
     */
    public static final String DATA = "aaData";

    /**
     * Name of the request parameter holding the string entered by user as data filter.
     */
    public static final String SEARCH_STRING = "sSearch";

    /**
     * Determines sorting data in descending order.
     *
     * @see #SORT_DIR
     */
    public static final String DESC = "desc";

    /**
     * Name of the request parameter holding the number of visible records.
     */
    public static final String DISPLAY_LENGTH = "iDisplayLength";

    /**
     * Name of the request parameter holding the index of first visible record.
     */
    public static final String DISPLAY_START = "iDisplayStart";

    /**
     * Name of the JSON result property holding the number of records matching DataTables level filtering.
     */
    public static final String FILTERED_NO = "iTotalDisplayRecords";

    /**
     * Name prefix for the request parameters holding index of column used in sorting.
     *
     * <p>
     *     There will be exactly <em>n</em> parameters related to sorting data (and having this prefix),
     *     where <em>n</em> is the number of columns used in sorting data. The parameters are named: iSortCol_0, ...
     *     and so on.
     * </p>
     * @see #SORT_COLS_NO
     */
    public static final String SORT_COL_PREFIX = "iSortCol_";

    /**
     * Name of the request parameter holding the number of columns used in sorting data.
     */
    public static final String SORT_COLS_NO = "iSortingCols";

    /**
     * Name prefix for the request parameters holding direction used in sorting of specific column.
     *
     * <p>
     *     There will be exactly <em>n</em> parameters related to sorting data (and having this prefix),
     *     where <em>n</em> is the number of columns used in sorting data. The parameters are named: sSortDir_0, ...
     *     and so on.
     * </p>
     * @see #SORT_COLS_NO
     */
    public static final String SORT_DIR = "sSortDir_";

    /**
     * Name of the JSON result property holding the total number of records.
     */
    public static final String TOTAL_NO = "iTotalRecords";

    private JQueryDataTables() {
        super();
    }
}
