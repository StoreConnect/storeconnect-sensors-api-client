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
package com.github.storeconnect.sensors.api.client.model.motion;

/**
 * Enumerates available {@link org.geojson.Feature}'s properties that can be used within the StoreConnect context
 * <p>
 * Each {@link FeatureProperty} is composed of two parts:
 * <ul>
 * <li>its name</li>
 * <li>the type of its value</li>
 * </ul>
 *
 * @author Aurelien Bourdon
 */
public enum FeatureProperty {

    BUILDING("building", Integer.class),
    FLOOR("floor", Integer.class),
    VENUE_ID("venueId", Integer.class);

    private final String name;
    private final Class<?> valueType;

    FeatureProperty(final String name, final Class<?> valueType) {
        this.name = name;
        this.valueType = valueType;
    }

    /**
     * Get the name of this {@link FeatureProperty}
     *
     * @return the name of this {@link FeatureProperty}
     */
    public String getName() {
        return name;
    }

    /**
     * Get the type of the value associated to this {@link FeatureProperty}
     *
     * @return the type of the value associated to this {@link FeatureProperty}
     */
    public Class<?> getValueType() {
        return valueType;
    }

}
