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
package fr.inria.lille.storeconnect.sensors.api.client.model.builder;

import de.fraunhofer.iosb.ilt.sta.model.FeatureOfInterest;
import de.fraunhofer.iosb.ilt.sta.model.builder.api.AbstractFeatureOfInterestBuilder.ValueCode;
import de.fraunhofer.iosb.ilt.sta.model.builder.api.BuildingException;
import fr.inria.lille.storeconnect.sensors.api.client.model.motion.FeatureProperty;
import org.geojson.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * {@link FeatureOfInterestBuilder}'s unit tests
 *
 * @author Aurelien Bourdon
 */
@DisplayName("FeatureOfInterestBuilder")
public class FeatureOfInterestBuilderTest {

    @Test
    @DisplayName("A FeatureOfInterestBuilder must always set the ValueCode#GeoJson as encodingType even if it is missing")
    public void testFeatureOfInterestBuilderWhenNotSpecifyingEncodingType() {
        final FeatureOfInterest featureOfInterest = FeatureOfInterestBuilder.builder().build();
        assertEquals(ValueCode.GeoJSON.getValue(), featureOfInterest.getEncodingType());
    }

    @Test
    @DisplayName("A FeatureOfInterestBuilder must only handle the ValueCode#GeoJson as encodingType")
    public void testFeatureOfInterestBuilderWhenSpecifyingEncodingType() {
        FeatureOfInterestBuilder.builder().encodingType(ValueCode.GeoJSON).build();
        Arrays.stream(ValueCode.values())
                .filter(valueCode -> !ValueCode.GeoJSON.equals(valueCode))
                .forEach(valueCode -> assertThrows(
                        BuildingException.class,
                        () -> FeatureOfInterestBuilder.builder().encodingType(valueCode), String.format("A FeatureOfInterestBuilder must not handle '%s' as encoding type", valueCode))
                );
    }

    @Test
    @DisplayName("A FeatureOfInterestBuilder must only handle GeoJson Feature as feature")
    public void testFeatureOfInterestBuilderWhenSpecifyingFeature() {
        final Feature feature = new Feature();
        assertEquals(feature, FeatureOfInterestBuilder.builder().feature(feature).build().getFeature(), "A FeatureOfInterestBuilder can handle explicit GeoJson Feature type as feature");
        assertEquals(feature, FeatureOfInterestBuilder.builder().feature((Object) feature).build().getFeature(), "A FeatureOfInterestBuilder can handle implicit GeoJson Feature type as feature");
        assertThrows(BuildingException.class, () -> FeatureOfInterestBuilder.builder().feature("wrong"), "A FeatureOfInterestBuilder must not handle other type than GeoJson Feature as a feature");
    }

    @Test
    @DisplayName("A FeatureOfInterestBuilder can define a venueId parameter inside its feature")
    public void testFeatureOfInterestBuilderWhenSpecifyingVenueIdFeatureParameter() {
        final Feature feature = new Feature();
        feature.setProperty(FeatureProperty.VENUE_ID.getName(), 1);
        final FeatureOfInterest featureOfInterest = FeatureOfInterestBuilder.builder().feature(feature).build();
        assertEquals(1, ((Feature) featureOfInterest.getFeature()).getProperties().get(FeatureProperty.VENUE_ID.getName()));
    }

    @Test
    @DisplayName("A FeatureOfInterestBuilder cannot define a venueId parameter inside its feature with a wrong value type")
    public void testFeatureOfInterestBuilderWhenSpecifyingVenueIdFeatureParameterWithWrongValueType() {
        final Feature feature = new Feature();
        feature.setProperty(FeatureProperty.VENUE_ID.getName(), "wrong");
        assertThrows(BuildingException.class, () -> FeatureOfInterestBuilder.builder().feature(feature), "A FeatureOfInterestBuilder cannot define a venueId parameter with different value type as expected");
        feature.setProperty(FeatureProperty.VENUE_ID.getName(), null);
        assertThrows(BuildingException.class, () -> FeatureOfInterestBuilder.builder().feature(feature), "A FeatureOfInterestBuilder cannot define a venueId parameter with null value type");
    }

}
