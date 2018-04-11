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
package fr.inria.lille.storeconnect.sensors.api.client.model.feature;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.geojson.LngLatAlt;
import org.geojson.Point;
import org.geojson.Polygon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * {@link GeometryTypedFeature}' unit tests
 *
 * @author Aurelien Bourdon
 */
@DisplayName("GeometryTypedFeature")
public class GeometryTypedFeatureTest {

    private Polygon associatedPolygon;
    private GeometryTypedFeature<Polygon> geometryTypedFeature;

    @BeforeEach
    public void setUp() {
        associatedPolygon = new Polygon(new LngLatAlt(1.0, 2.0), new LngLatAlt(3.0, 4.0));
        geometryTypedFeature = new GeometryTypedFeature<Polygon>(Polygon.class) {
            {
                setGeometry(associatedPolygon);
            }
        };
    }

    @Test
    @DisplayName("A GeometryTypedFeature can only handle its specified GeoJSON Geometry type as #geometry")
    public void testGeometryTypedFeatureWhenDefiningGeometry() {
        assertEquals(associatedPolygon, geometryTypedFeature.getGeometry(), "A GeometryTypedFeature#geometry can be set by its specific GeoJSON Geometry type");
        assertThrows(IllegalArgumentException.class, () -> geometryTypedFeature.setGeometry(new Point()), "A GeometryTypedFeature can only handle its specified #geometry type");
    }

    @Test
    @DisplayName("A GeometryTypedFeature has to be serialized as a Feature")
    public void testGeometryTypedFeatureSerialization() throws JsonProcessingException {
        assertEquals(
                "{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[1.0,2.0],[3.0,4.0]]]}}",
                new ObjectMapper().writeValueAsString(geometryTypedFeature),
                "A GeometryTypedFeature can only handle its specified #geometry type"
        );
    }

}
