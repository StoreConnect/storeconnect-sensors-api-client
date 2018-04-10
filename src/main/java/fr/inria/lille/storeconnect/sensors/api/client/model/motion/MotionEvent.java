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
package fr.inria.lille.storeconnect.sensors.api.client.model.motion;

import com.fasterxml.jackson.annotation.JsonInclude;
import fr.inria.lille.storeconnect.sensors.api.client.model.ObservationResult;
import fr.inria.lille.storeconnect.sensors.api.client.model.feature.PointFeature;

/**
 * Human motion caught by a StoreConnect's {@link de.fraunhofer.iosb.ilt.sta.model.Sensor}
 *
 * @author Aurelien Bourdon
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MotionEvent implements ObservationResult {

    private PointFeature location;
    private Float orientation;
    private MotionSubject subject;

    /**
     * Get the location associated to this {@link MotionEvent}
     *
     * @return the location associated to this {@link MotionEvent}
     */
    public PointFeature getLocation() {
        return location;
    }

    /**
     * Set the location associated to this {@link MotionEvent}
     *
     * @param location the location to set to this {@link MotionEvent}
     */
    public void setLocation(final PointFeature location) {
        this.location = location;
    }

    /**
     * Get the orientation associated to this {@link MotionEvent}
     *
     * @return the orientation associated to this {@link MotionEvent}
     */
    public Float getOrientation() {
        return orientation;
    }

    /**
     * Set the orientation associated to this {@link MotionEvent}
     *
     * @param orientation the orientation to set to this {@link MotionEvent}
     */
    public void setOrientation(final Float orientation) {
        this.orientation = orientation;
    }

    /**
     * Get the {@link MotionSubject} associated to this {@link MotionEvent}
     *
     * @return the {@link MotionSubject} associated to this {@link MotionEvent}
     */
    public MotionSubject getSubject() {
        return subject;
    }

    /**
     * Set the {@link MotionSubject} associated to this {@link MotionEvent}
     *
     * @param subject the {@link MotionSubject} to set to this {@link MotionEvent}
     */
    public void setSubject(final MotionSubject subject) {
        this.subject = subject;
    }

}