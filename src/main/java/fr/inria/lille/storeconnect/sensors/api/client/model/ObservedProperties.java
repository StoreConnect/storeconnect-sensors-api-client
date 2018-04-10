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
package fr.inria.lille.storeconnect.sensors.api.client.model;

import de.fraunhofer.iosb.ilt.sta.model.ObservedProperty;
import de.fraunhofer.iosb.ilt.sta.model.builder.ObservedPropertyBuilder;

import java.net.URI;

/**
 * Set of StoreConnect Sensor API's {@link ObservedProperty} instances
 *
 * @author Aurelien Bourdon
 */
public final class ObservedProperties {

    /**
     * The human motion {@link ObservedProperty}
     * <p>
     * This instance should be created first into the associated StoreConnect Sensor API's server and be set with the associated identifier.
     */
    public static final ObservedProperty MOTION = ObservedPropertyBuilder.builder()
            .name("Motion")
            // TODO define a real URI that points to the JSON Schema of this Motion ObservedProperty
            .definition(URI.create("http://storeconnect/observedproperties/motion"))
            .description("A human motion.")
            .build();

    private ObservedProperties() {
    }

}
