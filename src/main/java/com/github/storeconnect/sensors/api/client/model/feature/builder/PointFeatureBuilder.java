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
package com.github.storeconnect.sensors.api.client.model.feature.builder;

import com.github.storeconnect.sensors.api.client.model.feature.PointFeature;
import org.geojson.Point;

/**
 * {@link PointFeature} {@link de.fraunhofer.iosb.ilt.sta.model.builder.api.Builder}
 *
 * @author Aurelien Bourdon
 */
public final class PointFeatureBuilder extends GeometryTypedFeatureBuilder<Point, PointFeature, PointFeatureBuilder> {

    private PointFeatureBuilder() {
    }

    public static PointFeatureBuilder builder() {
        return new PointFeatureBuilder();
    }

    @Override
    protected PointFeatureBuilder getSelf() {
        return this;
    }

    @Override
    protected PointFeature newBuildingInstance() {
        return new PointFeature();
    }

}
