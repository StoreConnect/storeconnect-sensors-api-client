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
package com.github.storeconnect.sensors.api.clent.model.builder;

import com.github.storeconnect.sensors.api.client.model.builder.LocationBuilder;
import com.github.storeconnect.sensors.api.client.model.feature.PointFeature;
import com.github.storeconnect.sensors.api.client.model.motion.FeatureProperty;
import de.fraunhofer.iosb.ilt.sta.model.Location;
import de.fraunhofer.iosb.ilt.sta.model.builder.api.AbstractLocationBuilder.ValueCode;
import de.fraunhofer.iosb.ilt.sta.model.builder.api.BuildingException;
import org.geojson.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * {@link LocationBuilder}'s unit tests
 *
 * @author Aurelien Bourdon
 */
@DisplayName("LocationBuilder")
public class LocationBuilderTest {

    @Test
    @DisplayName("A LocationBuilder must always set the ValueCode#GeoJson as encodingType even if it is missing")
    public void testLocationBuilderWhenNotSpecifyingEncodingType() {
        final Location location = LocationBuilder.builder().build();
        assertEquals(ValueCode.GeoJSON.getValue(), location.getEncodingType());
    }

    @Test
    @DisplayName("A LocationBuilder must only handle the ValueCode#GeoJson as encodingType")
    public void testLocationBuilderWhenSpecifyingEncodingType() {
        LocationBuilder.builder().encodingType(ValueCode.GeoJSON).build();
        Arrays.stream(ValueCode.values())
                .filter(valueCode -> !ValueCode.GeoJSON.equals(valueCode))
                .forEach(valueCode -> assertThrows(
                        BuildingException.class,
                        () -> LocationBuilder.builder().encodingType(valueCode), String.format("A LocationBuilder must not handle '%s' as encoding type", valueCode))
                );
    }

    @Test
    @DisplayName("A LocationBuilder must only handle PointFeature as location")
    public void testLocationBuilderWhenSpecifyingFeature() {
        final PointFeature location = new PointFeature();
        assertEquals(location, LocationBuilder.builder().location(location).build().getLocation(), "A LocationBuilder can handle explicit PointFeature type as location");
        assertEquals(location, LocationBuilder.builder().location((Object) location).build().getLocation(), "A LocationBuilder can handle implicit PointFeature type as location");
        assertThrows(BuildingException.class, () -> LocationBuilder.builder().location("wrong"), "A LocationBuilder must not handle other type than PointFeature as a location");
    }

    @Test
    @DisplayName("A LocationBuilder can define a venueId parameter inside its feature")
    public void testLocationBuilderWhenSpecifyingVenueIdFeatureParameter() {
        final PointFeature pointLocation = new PointFeature();
        pointLocation.setProperty(FeatureProperty.VENUE_ID.getName(), 1);
        final Location location = LocationBuilder.builder().location(pointLocation).build();
        assertEquals(1, ((Feature) location.getLocation()).getProperties().get(FeatureProperty.VENUE_ID.getName()));
    }

    @Test
    @DisplayName("A LocationBuilder cannot define a venueId parameter inside its feature with a wrong value type")
    public void testLocationBuilderWhenSpecifyingVenueIdFeatureParameterWithWrongValueType() {
        final PointFeature location = new PointFeature();
        location.setProperty(FeatureProperty.VENUE_ID.getName(), "wrong");
        assertThrows(BuildingException.class, () -> LocationBuilder.builder().location(location), "A LocationBuilder cannot define a venueId parameter with different value type as expected");
        location.setProperty(FeatureProperty.VENUE_ID.getName(), null);
        assertThrows(BuildingException.class, () -> LocationBuilder.builder().location(location), "A LocationBuilder cannot define a venueId parameter with null value type");
    }

}
