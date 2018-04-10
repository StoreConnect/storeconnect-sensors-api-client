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

import fr.inria.lille.storeconnect.sensors.api.client.model.feature.GeometryTypedFeature;
import fr.inria.lille.storeconnect.sensors.api.client.model.feature.PointFeature;
import fr.inria.lille.storeconnect.sensors.api.client.model.motion.FeatureProperty;
import org.geojson.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link GeometryTypedFeatureBuilder}'s unit tests
 *
 * @author Aurelien Bourdon
 */
@DisplayName("GeometryTypedFeatureBuilder")
public class GeometryTypedFeatureBuilderTest {

    private DummyGeometryTypedFeatureBuilder geometryTypedFeatureBuilder;

    @BeforeEach
    public void setUp() {
        geometryTypedFeatureBuilder = new DummyGeometryTypedFeatureBuilder();
    }

    @Test
    @DisplayName("A GeometryTypedFeatureBuilder can handle FeatureProperty as property and convert it to its associated name")
    public void testGeometryTypedFeatureBuilderWhenSpecifyingProperty() {
        final GeometryTypedFeature<?> geometryTypedFeature = geometryTypedFeatureBuilder.property(FeatureProperty.BUILDING, 1).build();
        assertEquals(1, geometryTypedFeature.getProperties().get(FeatureProperty.BUILDING.getName()));
    }

    @Test
    @DisplayName("A GeometryTypedFeatureBuilder can handle FeaturePropertis as properties and convert them to their associated names")
    public void testGeometryTypedFeatureBuilderWhenSpecifyingProperties() {
        final GeometryTypedFeature<?> geometryTypedFeature = geometryTypedFeatureBuilder
                .properties(
                        new HashMap<FeatureProperty, Object>() {
                            {
                                put(FeatureProperty.BUILDING, 1);
                                put(FeatureProperty.FLOOR, 2);
                            }

                        }
                )
                .build();
        assertEquals(1, geometryTypedFeature.getProperties().get(FeatureProperty.BUILDING.getName()));
        assertEquals(2, geometryTypedFeature.getProperties().get(FeatureProperty.FLOOR.getName()));
    }

    private static class DummyGeometryTypedFeatureBuilder extends GeometryTypedFeatureBuilder<Point, PointFeature, DummyGeometryTypedFeatureBuilder> {

        @Override
        protected DummyGeometryTypedFeatureBuilder getSelf() {
            return this;
        }

        @Override
        protected PointFeature newBuildingInstance() {
            return new PointFeature();
        }

    }

}
