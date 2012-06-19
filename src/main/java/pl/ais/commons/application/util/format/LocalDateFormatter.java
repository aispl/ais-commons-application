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

package pl.ais.commons.application.util.format;

import java.text.ParseException;
import java.util.Locale;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.format.Formatter;

/**
 * {@link Formatter} implementation applicable to {@link LocalDate}.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public class LocalDateFormatter implements Formatter<LocalDate> {

    /**
     * Singleton instance.
     */
    public static final LocalDateFormatter INSTANCE = new LocalDateFormatter();

    private static final String DEFAULT_FORMAT = "MM/dd/yyyy";

    private transient final DateTimeFormatter dateFormatter;

    /**
     * Constructs new instance using default date format (MM/dd/yyyy).
     */
    public LocalDateFormatter() {
        this(DEFAULT_FORMAT);
    }

    /**
     * Constructs new instance using specified date format.
     *
     * @param dateFormat the date format to use
     */
    public LocalDateFormatter(final String dateFormat) {
        super();
        this.dateFormatter = DateTimeFormat.forPattern(dateFormat);
    }

    /**
     * @see org.springframework.format.Parser#parse(java.lang.String, java.util.Locale)
     */
    @Override
    @SuppressWarnings("PMD.NullAssignment")
    public LocalDate parse(final String text, final Locale locale) throws ParseException {
        final DateTimeFormatter formatter = dateFormatter.withLocale(locale);
        return (null == text) || (0 == text.length()) ? null : LocalDate.parse(text, formatter);
    }

    /**
     * @see org.springframework.format.Printer#print(java.lang.Object, java.util.Locale)
     */
    @Override
    @SuppressWarnings("PMD.NullAssignment")
    public String print(final LocalDate object, final Locale locale) {
        final DateTimeFormatter formatter = dateFormatter.withLocale(locale);
        return (null == object) ? null : formatter.print(object);
    }

}
