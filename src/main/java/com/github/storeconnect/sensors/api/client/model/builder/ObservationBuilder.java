/**
 * Copyright 2018 Inria Lille
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.storeconnect.sensors.api.client.model.builder;

import com.github.storeconnect.sensors.api.client.model.ObservationResult;
import de.fraunhofer.iosb.ilt.sta.model.builder.api.AbstractObservationBuilder;
import de.fraunhofer.iosb.ilt.sta.model.builder.api.BuildingException;

/**
 * Base class for any StoreConnect Sensor API's {@link de.fraunhofer.iosb.ilt.sta.model.builder.ObservationBuilder}
 *
 * @param <T> the concrete {@link ObservationResult} type handled by this {@link ObservationBuilder}
 * @param <U> the concrete type that extends this {@link ObservationBuilder}
 * @author Aurelien Bourdon
 */
public abstract class ObservationBuilder<T extends ObservationResult, U extends ObservationBuilder<T, U>> extends AbstractObservationBuilder<U> {

    private final Class<T> resultType;

    protected ObservationBuilder(final Class<T> resultType) {
        this.resultType = resultType;
    }

    @Override
    @SuppressWarnings("unchecked")
    public U result(final Object result) {
        if (result == null) {
            return getSelf();
        }
        if (!resultType.isAssignableFrom(result.getClass())) {
            throw new BuildingException(String.format("%s builder can only accept %s as #result type", getClass().getSimpleName(), resultType));
        }
        return result((T) result);
    }

    public U result(final T result) {
        super.result(result);
        return getSelf();
    }

}
