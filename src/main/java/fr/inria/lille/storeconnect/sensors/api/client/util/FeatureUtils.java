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
package fr.inria.lille.storeconnect.sensors.api.client.util;

import fr.inria.lille.storeconnect.sensors.api.client.model.motion.FeatureProperty;
import org.geojson.Feature;
import org.geojson.GeoJsonObject;

/**
 * Give some utilities when dealing with a {@link Feature}
 *
 * @author Aurelien Bourdon
 */
public final class FeatureUtils {

    /**
     * Check if a mandatory {@link FeatureProperty} exists in the given {@link Feature}.
     * <p>
     * Check pass iif:
     * <ul>
     * <li>the given {@link Feature} contains a property with a name equals to the given {@link FeatureProperty#name}</li>
     * <li>AND the {@link Feature}'s property with the name equals to the given {@link FeatureProperty#name} has a value type assignable from {@link FeatureProperty#valueType}</li>
     * </ul>
     *
     * @param from     the {@link Feature} from which checking the associated {@link Feature#properties}
     * @param property the mandatory {@link Feature}'s property to check
     * @throws InvalidGeoJsonObjectPropertyDefinition if the given {@link Feature} contains an invalid GeoJSON object's property definition
     */
    public static void checkPropertyDefinition(final Feature from, final FeatureProperty property) {
        checkPropertyDefinition(from, property, true);
    }

    /**
     * Check if the given {@link Feature} contains and respect the given {@link FeatureProperty} definition.
     * <p>
     * Check pass iif:
     * <ul>
     * <li>{@code failIfMissing} is {@code false}</li>
     * <li>
     * <ul>
     * <li>AND the given {@link Feature} does not contains a property with a name equals to the given {@link FeatureProperty#name}</li>
     * </ul>
     * </li>
     * <li>OR {@code failIfMissing} is {@code true}</li>
     * <li>
     * <ul>
     * <li>AND the given {@link Feature} contains a property with a name equals to the given {@link FeatureProperty#name}</li>
     * <li>AND the {@link Feature}'s property with the name equals to the given {@link FeatureProperty#name} has a value type assignable from {@link FeatureProperty#valueType}</li>
     * </ul>
     * </li>
     * </ul>
     *
     * @param from          the {@link Feature} from which checking the associated {@link Feature#properties}
     * @param property      the mandatory {@link Feature}'s property to check
     * @param failIfMissing {@code true} if property is mandatory, {@code false} otherwise
     * @throws InvalidGeoJsonObjectPropertyDefinition if the given {@link Feature} contains an invalid GeoJSON object's property definition
     */
    public static void checkPropertyDefinition(final Feature from, final FeatureProperty property, final boolean failIfMissing) {
        if (from.getProperties() == null || !from.getProperties().containsKey(property.getName())) {
            if (failIfMissing) {
                throw new InvalidGeoJsonObjectPropertyDefinition(from, property, "property missing");
            }
            return;
        }
        final Object fromPropertyValue = from.getProperties().get(property.getName());
        if (fromPropertyValue == null) {
            throw new InvalidGeoJsonObjectPropertyDefinition(from, property, "null value");
        }
        if (!property.getValueType().isAssignableFrom(fromPropertyValue.getClass())) {
            throw new InvalidGeoJsonObjectPropertyDefinition(from, property, "invalid value type");
        }
    }

    /**
     * Raised when a {@link Feature} contains an invalid {@link Feature#properties} definition
     *
     * @author Aurelien Bourdon
     */
    public static class InvalidGeoJsonObjectPropertyDefinition extends IllegalArgumentException {
        public InvalidGeoJsonObjectPropertyDefinition(final GeoJsonObject from, final FeatureProperty expected, final String reason) {
            super(String.format("Invalid GeoJSON Feature's property '%s' from GeoJSON Feature '%s'. Reason: %s", expected.getName(), from, reason));
        }
    }

}
