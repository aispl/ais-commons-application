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

package pl.ais.commons.application.feature;

import javax.annotation.Nonnull;

/**
 * Defines the API contract for features holder.
 *
 * @author Warlock, AIS.PL
 * @since 1.0.1
 */
public interface FeaturesHolder {

    /**
     * Adds new feature.
     *
     * @param feature the feature to add
     * @param handler the feature handler
     */
    void addFeature(@Nonnull final Class<?> feature, @Nonnull final Object handler);

    /**
     * Adds new virtual feature.
     *
     * @param feature the feature to add
     */
    void addVirtualFeature(@Nonnull final Class<?> feature);

    /**
     * Retrieves the feature.
     *
     * @param feature feature request
     * @return the requested feature
     * @throws UnsupportedFeatureException if the requested feature is not owned by this holder
     * @throws VirtualFeatureException if the requested feature is virtual (has no implementation)
     */
    @Nonnull
    <T> T getFeature(@Nonnull final Class<T> feature) throws UnsupportedFeatureException, VirtualFeatureException;

    /**
     * Verifies if requested feature is owned by the holder.
     *
     * @param feature feature request
     * @return {@code true} if the feature is owned by the holder, {@code false} otherwise
     */
    boolean hasFeature(@Nonnull final Class<?> feature);

    /**
     * Removes the feature.
     *
     * @param feature the feature to remove
     */
    void removeFeature(@Nonnull final Class<?> feature);

}
