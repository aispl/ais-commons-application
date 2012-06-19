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

package pl.ais.commons.application.util.property;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.Formatter;

/**
 * {@link PropertyEditor} delegating the work to {@link Formatter}.
 *
 * @param <T> determines the serviced type
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public class DelegatingPropertyEditor<T> extends PropertyEditorSupport {

    /**
     * Adapts given {@link Formatter formatter} as {@link PropertyEditor property editor}.
     *
     * @param formatter the formatter to adapt
     * @return new {@link PropertyEditor property editor} instance backed by given formatter.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static PropertyEditor adapt(final Formatter<?> formatter) {
        return new DelegatingPropertyEditor(formatter);
    }

    private final transient Formatter<T> formatter;

    /**
     * Constructs new instance.
     *
     * @param formatter
     */
    public DelegatingPropertyEditor(final Formatter<T> formatter) {
        super();
        this.formatter = formatter;
    }

    /**
     * @see java.beans.PropertyEditorSupport#getAsText()
     */
    @Override
    public String getAsText() {
        return formatter.print((T) getValue(), LocaleContextHolder.getLocale());
    }

    /**
     * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
     */
    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        try {
            setValue(formatter.parse(text, LocaleContextHolder.getLocale()));
        } catch (ParseException exception) {
            throw new IllegalArgumentException(exception);
        }
    }
}
