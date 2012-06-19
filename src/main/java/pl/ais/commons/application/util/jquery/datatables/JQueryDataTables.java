/*
 * Copyright (c) 2012, AIS.PL
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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
