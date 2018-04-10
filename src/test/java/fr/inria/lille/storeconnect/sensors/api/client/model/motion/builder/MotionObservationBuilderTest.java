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
package fr.inria.lille.storeconnect.sensors.api.client.model.motion.builder;

import de.fraunhofer.iosb.ilt.sta.model.builder.api.BuildingException;
import fr.inria.lille.storeconnect.sensors.api.client.model.feature.PointFeature;
import fr.inria.lille.storeconnect.sensors.api.client.model.motion.FeatureProperty;
import fr.inria.lille.storeconnect.sensors.api.client.model.motion.MotionEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * {@link MotionObservationBuilder}'s unit tests
 *
 * @author Aurelien Bourdon
 */
@DisplayName("MotionObservationBuilder")
public class MotionObservationBuilderTest {

    private MotionEvent motionEvent;

    @BeforeEach
    public void setUp() {
        motionEvent = MotionEventBuilder.builder()
                .subject(MotionSubjectBuilder.builder().id("subjectId").build())
                .location(new PointFeature() {
                    {
                        setProperty(FeatureProperty.BUILDING.getName(), 1);
                        setProperty(FeatureProperty.FLOOR.getName(), 2);
                    }
                })
                .build();
    }

    @Test
    @DisplayName("A MotionObservationBuilder can only handle MotionEvent as #result")
    public void testMotionObservationBuilderWhenDefiningResultType() {
        assertAll(
                () -> assertNull(MotionObservationBuilder.builder().result(null).build().getResult(), "A MotionObservationBuilder can handle null #result"),
                () -> assertEquals(motionEvent, MotionObservationBuilder.builder().result(motionEvent).build().getResult(), "A MotionObservationBuilder can handle explicit #result type"),
                () -> assertEquals(motionEvent, MotionObservationBuilder.builder().result((Object) motionEvent).build().getResult(), "A MotionObservationBuilder can handle implicit #result type"),
                () -> assertThrows(BuildingException.class, () -> MotionObservationBuilder.builder().result("wrong"), "A MotionObservationBuilder cannot handle invalid #result type")
        );
    }

    @Test
    @DisplayName("A MotionObservationBuilder cannot build an Observation with a #result which does not contain the 'floor' and the 'building' GeoJSON Feature properties")
    public void testMotionObservationBuilderWhenDefiningResultWithoutBuildingAndFloorGeoJSONFeatureProperties() {
        motionEvent.getLocation().getProperties().remove(FeatureProperty.BUILDING.getName());
        motionEvent.getLocation().getProperties().remove(FeatureProperty.FLOOR.getName());
        assertThrows(BuildingException.class, () -> MotionObservationBuilder.builder().result(motionEvent));
    }

    @Test
    @DisplayName("A MotionObservationBuilder cannot build an Observation with a #result which does not contain the 'building' GeoJSON Feature properties")
    public void testMotionObservationBuilderWhenDefiningResultWithoutBuildingGeoJSONFeatureProperties() {
        motionEvent.getLocation().getProperties().remove(FeatureProperty.FLOOR.getName());
        assertThrows(BuildingException.class, () -> MotionObservationBuilder.builder().result(motionEvent));
    }

    @Test
    @DisplayName("A MotionObservationBuilder cannot build an Observation with a #result which does not contain the 'floor' GeoJSON Feature properties")
    public void testMotionObservationBuilderWhenDefiningResultWithoutFloorGeoJSONFeatureProperties() {
        motionEvent.getLocation().getProperties().remove(FeatureProperty.BUILDING.getName());
        assertThrows(BuildingException.class, () -> MotionObservationBuilder.builder().result(motionEvent));
    }

    @Test
    @DisplayName("A MotionObservationBuilder cannot build an Observation with a valid #result which does not contain a MotionSubject")
    public void testMotionObservationBuilderWhenDefiningResultWithValidGeoJSONFeatureProperties() {
        motionEvent.setSubject(null);
        assertThrows(BuildingException.class, () -> MotionObservationBuilder.builder().result(motionEvent));
    }

}
