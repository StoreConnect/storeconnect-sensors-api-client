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
package com.github.storeconnect.sensors.api.clent.util;

import com.github.storeconnect.sensors.api.client.model.motion.FeatureProperty;
import com.github.storeconnect.sensors.api.client.util.FeatureUtils;
import com.github.storeconnect.sensors.api.client.util.FeatureUtils.InvalidGeoJsonObjectPropertyDefinition;
import org.geojson.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * {@link FeatureUtils} uni tests
 *
 * @author Aurelien Bourdon
 */
@DisplayName("FeatureUtils")
public class FeatureUtilsTest {

    private Feature testedFeature;

    @BeforeEach
    public void setUp() {
        testedFeature = new Feature();
    }

    @Test
    @DisplayName("FeatureUtils can check property definition with existing mandatory valid property")
    public void checkPropertyDefinitionWithExistingMandatoryValidProperty() {
        testedFeature.setProperty(FeatureProperty.BUILDING.getName(), 1);
        FeatureUtils.checkPropertyDefinition(testedFeature, FeatureProperty.BUILDING);
    }

    @Test
    @DisplayName("FeatureUtils can check property definition with existing mandatory invalid property")
    public void checkPropertyDefinitionWithExistingMandatoryInvalidProperty() {
        testedFeature.setProperty(FeatureProperty.BUILDING.getName(), "wrong");
        assertThrows(InvalidGeoJsonObjectPropertyDefinition.class, () -> FeatureUtils.checkPropertyDefinition(testedFeature, FeatureProperty.BUILDING), "FeatureUtils can check property definition with existing mandatory property but invalid type value");
        testedFeature.setProperty(FeatureProperty.BUILDING.getName(), null);
        assertThrows(InvalidGeoJsonObjectPropertyDefinition.class, () -> FeatureUtils.checkPropertyDefinition(testedFeature, FeatureProperty.BUILDING), "FeatureUtils can check property definition with existing mandatory property but null value");
    }

    @Test
    @DisplayName("FeatureUtils can check property definition with non existing mandatory property")
    public void checkPropertyDefinitionWithNonExistingMandatoryProperty() {
        assertThrows(InvalidGeoJsonObjectPropertyDefinition.class, () -> FeatureUtils.checkPropertyDefinition(testedFeature, FeatureProperty.BUILDING));
    }

    @Test
    @DisplayName("FeatureUtils can check property definition with non existing optional property")
    public void checkPropertyDefinitionWithNonExistingOptionalProperty() {
        FeatureUtils.checkPropertyDefinition(testedFeature, FeatureProperty.BUILDING, false);
    }

    @Test
    @DisplayName("FeatureUtils can check property definition with mandatory property and null Feature's properties")
    public void checkPropertyDefinitionWithMandatoryPropertyAndNullFeatureProperties() {
        testedFeature.setProperties(null);
        assertThrows(InvalidGeoJsonObjectPropertyDefinition.class, () -> FeatureUtils.checkPropertyDefinition(testedFeature, FeatureProperty.BUILDING));
    }

    @Test
    @DisplayName("FeatureUtils can check property definition with optional property and null Feature's properties")
    public void checkPropertyDefinitionWithOptionalPropertyAndNullFeatureProperties() {
        testedFeature.setProperties(null);
        FeatureUtils.checkPropertyDefinition(testedFeature, FeatureProperty.BUILDING, false);
    }

}
