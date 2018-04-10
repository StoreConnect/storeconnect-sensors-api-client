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
package fr.inria.lille.storeconnect.sensors.api.client.model.builder;

import de.fraunhofer.iosb.ilt.sta.model.Datastream;
import de.fraunhofer.iosb.ilt.sta.model.builder.api.AbstractDatastreamBuilder;
import de.fraunhofer.iosb.ilt.sta.model.builder.api.BuildingException;

/**
 * StoreConnect Sensor API specialization of the {@link de.fraunhofer.iosb.ilt.sta.model.builder.DatastreamBuilder}
 * <p>
 * A StoreConnect Sensor API's {@link DatastreamBuilder} is:
 * <ul>
 * <li>based on an {@link AbstractDatastreamBuilder}</li>
 * <li>always set with {@link AbstractDatastreamBuilder.ValueCode#OM_Observation} as {@link Datastream#observationType}. So, any created {@link Datastream} instance will be set by this value if missing.</li>
 * </ul>
 *
 * @author Aurelien Bourdon
 */
public final class DatastreamBuilder extends AbstractDatastreamBuilder<DatastreamBuilder> {

    private DatastreamBuilder() {
    }

    public static DatastreamBuilder builder() {
        return new DatastreamBuilder();
    }

    @Override
    protected DatastreamBuilder getSelf() {
        return this;
    }

    @Override
    public Datastream build() {
        observationType(ValueCode.OM_Observation);
        return super.build();
    }

    @Override
    public DatastreamBuilder observationType(final ValueCode observationType) {
        if (!ValueCode.OM_Observation.equals(observationType)) {
            throw new BuildingException("StoreConnect Sensor API's Datastream must only handle OM_Observation observation type");
        }
        return super.observationType(observationType);
    }

}
