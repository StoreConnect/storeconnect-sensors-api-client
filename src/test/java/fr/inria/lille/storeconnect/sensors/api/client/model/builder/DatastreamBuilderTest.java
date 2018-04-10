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
import de.fraunhofer.iosb.ilt.sta.model.builder.api.AbstractDatastreamBuilder.ValueCode;
import de.fraunhofer.iosb.ilt.sta.model.builder.api.BuildingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * {@link DatastreamBuilder}'s unit tests
 *
 * @author Aurelien Bourdon
 */
@DisplayName("DatastreamBuilder")
public class DatastreamBuilderTest {

    @Test
    @DisplayName("A DatastreamBuilder must always set the ValueCode#OM_Observation as observationType even if it is missing")
    public void testDatastreamBuilderWhenNotSpecifyingObservationType() {
        final Datastream datastream = DatastreamBuilder.builder().build();
        assertEquals(ValueCode.OM_Observation.getValue(), datastream.getObservationType());
    }

    @Test
    @DisplayName("A DatastreamBuilder must only handle the ValueCode#OM_Observation as observationType")
    public void testDatastreamBuilderWhenSpecifyingObservationType() {
        DatastreamBuilder.builder().observationType(ValueCode.OM_Observation).build();
        Arrays.stream(ValueCode.values())
                .filter(valueCode -> !ValueCode.OM_Observation.equals(valueCode))
                .forEach(valueCode -> assertThrows(
                        BuildingException.class,
                        () -> DatastreamBuilder.builder().observationType(valueCode), String.format("A DatastreamBuilder must not handle '%s' as observation type", valueCode))
                );
    }

}
