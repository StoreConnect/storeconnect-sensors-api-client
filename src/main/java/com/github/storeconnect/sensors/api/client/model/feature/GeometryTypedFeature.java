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
package com.github.storeconnect.sensors.api.client.model.feature;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.geojson.Feature;
import org.geojson.GeoJsonObject;

/**
 * A specific {@link Feature} that only accepts {@code T} as {@link Feature#geometry} type.
 *
 * @param <T> the {@link GeoJsonObject} type that be only accepted as a {@link Feature#geometry} type by this {@link Feature}
 * @author Aurelien Bourdon
 */
@JsonTypeName("Feature")
public abstract class GeometryTypedFeature<T extends GeoJsonObject> extends Feature {

    @JsonIgnore
    private final Class<T> geometryType;

    protected GeometryTypedFeature(final Class<T> geometryType) {
        this.geometryType = geometryType;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getGeometry() {
        return (T) super.getGeometry();
    }

    @Override
    public void setGeometry(final GeoJsonObject geometry) {
        if (geometry == null) {
            return;
        }
        if (!geometryType.isAssignableFrom(geometry.getClass())) {
            throw new IllegalArgumentException("Can only handle geometry type of " + geometryType);
        }
        super.setGeometry(geometry);
    }

}
