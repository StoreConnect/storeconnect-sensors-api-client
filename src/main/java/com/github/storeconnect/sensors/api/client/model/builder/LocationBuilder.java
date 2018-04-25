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

import com.github.storeconnect.sensors.api.client.model.feature.PointFeature;
import com.github.storeconnect.sensors.api.client.model.motion.FeatureProperty;
import de.fraunhofer.iosb.ilt.sta.model.Location;
import de.fraunhofer.iosb.ilt.sta.model.builder.api.AbstractLocationBuilder;
import de.fraunhofer.iosb.ilt.sta.model.builder.api.BuildingException;
import com.github.storeconnect.sensors.api.client.util.FeatureUtils;
import org.geojson.Point;

/**
 * StoreConnect Sensor API specialization of the {@link de.fraunhofer.iosb.ilt.sta.model.builder.LocationBuilder}
 * <p>
 * A StoreConnect Sensor API's {@link LocationBuilder}:
 * <ul>
 * <li>is based on an {@link AbstractLocationBuilder}</li>
 * <li>is always set with {@link AbstractLocationBuilder.ValueCode#GeoJSON} as {@link Location#encodingType}. So any {@link Location} will be set by this value if missing.</li>
 * <li>can only handle {@link Point} type as {@link Location#location}</li>
 * <li>could have a {@link FeatureProperty#VENUE_ID} as a {@link Location#location}'s property</li>
 * </ul>
 *
 * @author Aurelien Bourdon
 */
public final class LocationBuilder extends AbstractLocationBuilder<LocationBuilder> {

    private LocationBuilder() {
    }

    public static LocationBuilder builder() {
        return new LocationBuilder();
    }

    @Override
    public Location build() {
        encodingType(ValueCode.GeoJSON);
        return super.build();
    }

    @Override
    public LocationBuilder encodingType(final ValueCode encodingType) {
        if (!ValueCode.GeoJSON.equals(encodingType)) {
            throw new BuildingException("StoreConnect Sensor API's Location must only handle GeoJSON encoding type");
        }
        return super.encodingType(encodingType);
    }

    @Override
    public LocationBuilder location(final Object location) {
        if (location == null) {
            return getSelf();
        }
        if (!(location instanceof PointFeature)) {
            throw new BuildingException("StoreConnect Sensor API's Location can only handle PointFeature type as location");
        }
        return location((PointFeature) location);
    }

    @Override
    protected LocationBuilder getSelf() {
        return this;
    }

    public LocationBuilder location(final PointFeature location) {
        try {
            FeatureUtils.checkPropertyDefinition(location, FeatureProperty.VENUE_ID, false);
        } catch (final IllegalArgumentException e) {
            throw new BuildingException(e);
        }
        return super.location(location);
    }

}
