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
package com.github.storeconnect.sensors.api.client.model.motion.builder;

import de.fraunhofer.iosb.ilt.sta.model.builder.api.AbstractBuilder;
import com.github.storeconnect.sensors.api.client.model.feature.PointFeature;
import com.github.storeconnect.sensors.api.client.model.motion.MotionEvent;
import com.github.storeconnect.sensors.api.client.model.motion.MotionSubject;

/**
 * A {@link MotionEvent} {@link de.fraunhofer.iosb.ilt.sta.model.builder.api.Builder}
 *
 * @author Aurelien Bourdon
 */
public final class MotionEventBuilder extends AbstractBuilder<MotionEvent> {

    public static MotionEventBuilder builder() {
        return new MotionEventBuilder();
    }

    @Override
    protected MotionEvent newBuildingInstance() {
        return new MotionEvent();
    }

    public MotionEventBuilder subject(final MotionSubject subject) {
        getBuildingInstance().setSubject(subject);
        return this;
    }

    public MotionEventBuilder location(final PointFeature location) {
        getBuildingInstance().setLocation(location);
        return this;
    }

    public MotionEventBuilder orientation(final Float orientation) {
        getBuildingInstance().setOrientation(orientation);
        return this;
    }

}
