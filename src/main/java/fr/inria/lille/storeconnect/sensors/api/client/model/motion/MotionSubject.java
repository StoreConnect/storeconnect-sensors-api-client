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

/**
 * Describes identification information about the perceived {@link MotionEvent}. In other words: the {@link MotionEvent}'s target.
 *
 * @author Aurelien Bourdon
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MotionSubject {

    private String id;

    /**
     * Get the identifier of this {@link MotionSubject}
     *
     * @return the identifier of this {@link MotionSubject}
     */
    public String getId() {
        return id;
    }

    /**
     * Set the identifier of this {@link MotionSubject}
     *
     * @param id the identifier to set to this {@link MotionSubject}
     */
    public void setId(final String id) {
        this.id = id;
    }
}
