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

import com.github.storeconnect.sensors.api.client.model.motion.FeatureProperty;
import com.github.storeconnect.sensors.api.client.util.FeatureUtils;
import de.fraunhofer.iosb.ilt.sta.model.FeatureOfInterest;
import de.fraunhofer.iosb.ilt.sta.model.builder.api.AbstractFeatureOfInterestBuilder;
import de.fraunhofer.iosb.ilt.sta.model.builder.api.BuildingException;
import org.geojson.Feature;

/**
 * StoreConnect Sensor API specialization of the {@link de.fraunhofer.iosb.ilt.sta.model.builder.FeatureOfInterestBuilder}
 * <p>
 * A StoreConnect Sensor API's {@link FeatureOfInterestBuilder}:
 * <ul>
 * <li>is based on an {@link AbstractFeatureOfInterestBuilder}</li>
 * <li>is always set with {@link AbstractFeatureOfInterestBuilder.ValueCode#GeoJSON} as {@link FeatureOfInterest#encodingType}. So any created {@link FeatureOfInterest} instance will be set by this value if missing.</li>
 * <li>can only handle {@link Feature} type as {@link FeatureOfInterest#feature}</li>
 * <li>could have a {@link FeatureProperty#VENUE_ID} as a {@link FeatureOfInterest#feature}'s property</li>
 * </ul>
 *
 * @author Aurelien Bourdon
 */
public final class FeatureOfInterestBuilder extends AbstractFeatureOfInterestBuilder<FeatureOfInterestBuilder> {

    private FeatureOfInterestBuilder() {
    }

    public static FeatureOfInterestBuilder builder() {
        return new FeatureOfInterestBuilder();
    }

    @Override
    public FeatureOfInterest build() {
        encodingType(ValueCode.GeoJSON);
        return super.build();
    }

    @Override
    public FeatureOfInterestBuilder encodingType(final ValueCode encodingType) {
        if (!ValueCode.GeoJSON.equals(encodingType)) {
            throw new BuildingException("StoreConnect Sensor API's FeatureOfInterest must only handle GeoJSON encoding type");
        }
        return super.encodingType(encodingType);
    }

    @Override
    public FeatureOfInterestBuilder feature(final Object feature) {
        if (feature == null) {
            return getSelf();
        }
        if (!(feature instanceof Feature)) {
            throw new BuildingException("StoreConnect Sensor API's FeatureOfInterest can only handle GeoJSON Feature type as feature");
        }
        return feature((Feature) feature);
    }

    @Override
    protected FeatureOfInterestBuilder getSelf() {
        return this;
    }

    public FeatureOfInterestBuilder feature(final Feature feature) {
        try {
            FeatureUtils.checkPropertyDefinition(feature, FeatureProperty.VENUE_ID, false);
        } catch (final IllegalArgumentException e) {
            throw new BuildingException(e);
        }
        return super.feature(feature);
    }

}
