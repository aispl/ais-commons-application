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

import java.io.Serializable;

import org.apache.commons.lang3.ObjectUtils;

/**
 * Property change.
 *
 * @param <T> determines the type of property value
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
@SuppressWarnings("PMD.MissingSerialVersionUID")
public class PropertyChange<T> implements Serializable {

    private T finalValue;

    private T initialValue;

    private String propertyName;

    /**
     * Constructs new instance.
     *
     * @param propertyName
     * @param initialValue
     * @param finalValue
     */
    public PropertyChange(final String propertyName, final T initialValue, final T finalValue) {
        super();
        this.propertyName = propertyName;
        this.initialValue = initialValue;
        this.finalValue = finalValue;
    }

    /**
     * @return the finalValue
     */
    public T getFinalValue() {
        return finalValue;
    }

    /**
     * @return the initialValue
     */
    public T getInitialValue() {
        return initialValue;
    }

    /**
     * @return the propertyName
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * @return {@code true} if initial value equals final value, {@code false} otherwise
     */
    public boolean isIllusory() {
        return ObjectUtils.equals(initialValue, finalValue);
    }

    /**
     * @param finalValue the finalValue to set
     */
    public void setFinalValue(final T finalValue) {
        this.finalValue = finalValue;
    }

    /**
     * @param initialValue the initialValue to set
     */
    public void setInitialValue(final T initialValue) {
        this.initialValue = initialValue;
    }

    /**
     * @param propertyName the propertyName to set
     */
    public void setPropertyName(final String propertyName) {
        this.propertyName = propertyName;
    }

}
