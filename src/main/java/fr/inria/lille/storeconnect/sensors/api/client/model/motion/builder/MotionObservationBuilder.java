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

import de.fraunhofer.iosb.ilt.sta.model.Observation;
import de.fraunhofer.iosb.ilt.sta.model.builder.api.BuildingException;
import fr.inria.lille.storeconnect.sensors.api.client.model.builder.ObservationBuilder;
import fr.inria.lille.storeconnect.sensors.api.client.model.motion.FeatureProperty;
import fr.inria.lille.storeconnect.sensors.api.client.model.motion.MotionEvent;
import fr.inria.lille.storeconnect.sensors.api.client.util.FeatureUtils;

/**
 * A {@link MotionEvent} {@link ObservationBuilder}
 * <p>
 * Any new {@link de.fraunhofer.iosb.ilt.sta.model.Observation} created by this {@link MotionObservationBuilder} will have:
 * <ul>
 * <li>a {@link MotionEvent} as a {@link de.fraunhofer.iosb.ilt.sta.model.Observation#result}</li>
 * <li>a non {@code null} {@link MotionEvent#subject}</li>
 * <li>a {@link FeatureProperty#BUILDING} {@link org.geojson.Feature}'s property in its {@link MotionEvent#location}</li>
 * <li>a {@link FeatureProperty#FLOOR} {@link org.geojson.Feature}'s property in its {@link MotionEvent#location}</li>
 * </ul>
 *
 * @author Aurelien Bourdon
 */
public final class MotionObservationBuilder extends ObservationBuilder<MotionEvent, MotionObservationBuilder> {

    private MotionObservationBuilder() {
        super(MotionEvent.class);
    }

    public static MotionObservationBuilder builder() {
        return new MotionObservationBuilder();
    }

    @Override
    public MotionObservationBuilder result(final MotionEvent result) {
        checkResult(result);
        return super.result(result);
    }

    private static void checkResult(final MotionEvent result) {
        if (result == null) {
            return;
        }
        checkResultMotionSubject(result);
        checkResultLocation(result);
    }

    private static void checkResultMotionSubject(final MotionEvent result) {
        if (result.getSubject() == null) {
            throw new BuildingException("#result's subject cannot be null");
        }
    }

    private static void checkResultLocation(final MotionEvent result) {
        if (result.getLocation() == null) {
            throw new BuildingException("#result's location cannot be null");
        }
        try {
            FeatureUtils.checkPropertyDefinition(result.getLocation(), FeatureProperty.BUILDING);
            FeatureUtils.checkPropertyDefinition(result.getLocation(), FeatureProperty.FLOOR);
        } catch (final IllegalArgumentException e) {
            throw new BuildingException(e);
        }
    }

    @Override
    protected MotionObservationBuilder getSelf() {
        return this;
    }

    @Override
    public Observation build() {
        checkResult((MotionEvent) getBuildingInstance().getResult());
        return super.build();
    }

}
