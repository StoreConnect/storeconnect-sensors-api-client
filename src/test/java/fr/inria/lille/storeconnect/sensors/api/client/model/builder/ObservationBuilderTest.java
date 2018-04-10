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

import de.fraunhofer.iosb.ilt.sta.model.builder.api.BuildingException;
import fr.inria.lille.storeconnect.sensors.api.client.model.ObservationResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * {@link ObservationBuilder}'s unit tests
 *
 * @author Aurelien Bourdon
 */
@DisplayName("ObservationBuilder")
public class ObservationBuilderTest {

    @Test
    @DisplayName("An ObservationBuilder can handle expected result type")
    public void testObservationBuilderWhenDefiningResult() {
        final MyCustomObservationResult expected = new MyCustomObservationResult();
        assertEquals(expected, MyCustomObservationBuilder.builder().result(expected).build().getResult(), "An ObservationBuilder can handle explicit expected result type");
        assertEquals(expected, MyCustomObservationBuilder.builder().result((Object) expected).build().getResult(), "An ObservationBuilder can handle implicit expected result type");
        assertThrows(BuildingException.class, () -> MyCustomObservationBuilder.builder().result("wrong"), "An ObservationBuilder can only handle a specific result type");
    }

    private static class MyCustomObservationResult implements ObservationResult {
    }

    private static class MyCustomObservationBuilder extends ObservationBuilder<MyCustomObservationResult, MyCustomObservationBuilder> {

        protected MyCustomObservationBuilder() {
            super(MyCustomObservationResult.class);
        }

        public static MyCustomObservationBuilder builder() {
            return new MyCustomObservationBuilder();
        }

        @Override
        protected MyCustomObservationBuilder getSelf() {
            return this;
        }

    }

}
