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
package fr.inria.lille.storeconnect.sensors.api.client.model.feature.builder;

import de.fraunhofer.iosb.ilt.sta.model.builder.api.ExtensibleBuilder;
import fr.inria.lille.storeconnect.sensors.api.client.model.feature.GeometryTypedFeature;
import org.geojson.GeoJsonObject;

import java.util.Map;

/**
 * Base class for any {@link GeometryTypedFeature} {@link de.fraunhofer.iosb.ilt.sta.model.builder.api.Builder}
 * <p>
 * Any {@link GeometryTypedFeatureBuilder} is an {@link ExtensibleBuilder}, and can so be extended.
 *
 * @param <T> the specific type of {@link org.geojson.Feature#geometry}
 * @param <U> the specific type of {@link GeometryTypedFeature} this {@link GeometryTypedFeatureBuilder} can build
 * @param <V> the concrete type that extends this {@link GeometryTypedFeatureBuilder}
 * @author Aurelien Bourdon
 */
public abstract class GeometryTypedFeatureBuilder<T extends GeoJsonObject, U extends GeometryTypedFeature<T>, V extends GeometryTypedFeatureBuilder<T, U, V>> extends ExtensibleBuilder<U, V> {

    public V geometry(final T geometry) {
        getBuildingInstance().setGeometry(geometry);
        return getSelf();
    }

    public V property(final String key, final Object value) {
        getBuildingInstance().setProperty(key, value);
        return getSelf();
    }

    public V properties(final Map<String, Object> properties) {
        getBuildingInstance().setProperties(properties);
        return getSelf();
    }

}
