# FUI StoreConnect Sensor API's client

[![Build Status](https://travis-ci.org/StoreConnect/storeconnect-sensors-api-client.svg?branch=master)](https://travis-ci.org/StoreConnect/storeconnect-sensors-api-client)

Java-based client library for the [FUI StoreConnect Sensor API](https://github.com/StoreConnect/storeconnect-sensors-api).

Based on the [FROST-Client](https://github.com/FraunhoferIOSB/FROST-Client). 

## How to get it

### Via Maven Central

```xml
<depedency>
    <groupId>com.github.storeconnect</groupId>
    <artifactId>storeconnect-sensors-api-client</artifactId>
    <version>0.3</version>
</depedency>
```

### Compile from sources

Package can be built and installed by executing the following [Maven](https://maven.apache.org/) command from the root folder:

```bash
$ mvn install
```

## How to use it

### OGC SensorThings' features

As based on the [FROST-Client](https://github.com/FraunhoferIOSB/FROST-Client), the FUI StoreConnect Sensor API's client:

- Supports:
    - Operate CRUD operations on OGC SensorThings API's entities
    - Execute queries on entity sets
    - Load referenced entities
    - Is compatible with the MultiDatastream extension
    - Is compatible with the Data Array extension for creating observations only
- Does not support:
    - Batch requests
    - dataArray for requesting observations
    - MQTT

### API use case example

Let's say we have a phone (a `Thing`), named `MyPhone`, that contains a `Sensor`, named `MySensor`.
Let's say our `MySensor` can observes human motions, i.e., a `ObservedProperties#MOTION` `ObservedProperty`.

#### How could be the way to publish `MySensor`'s `Observation`s?

##### Simple example

Create one `ObservedProperties#MOTION` `ObservedProperty` can be done in the following way:

```java
// Create the access point to the StoreConnnect Sensor API's server
final SensorThingsService service = new SensorThingsService(URI.create("http://localhost:8080/SensorThingsService/v1.0").toURL());

// Create a new MotionEvent Observation
service.create(
        MotionObservationBuilder.builder()
                .phenomenonTime(new TimeObject(ZonedDateTime.now()))
                .datastream(datastream) // Predefined Datastream
                .featureOfInterest(featureOfInterest) // Predefined FeatureOfInterest
                .result(MotionEventBuilder.builder()
                        .subject(MotionSubjectBuilder.builder()
                                .id("subject1")
                                .build()
                        )
                        .location(PointFeatureBuilder.builder()
                                .geometry(new Point(new LngLatAlt(1.0, 2.0)))
                                .property(FeatureProperty.BUILDING.getName(), 1)
                                .property(FeatureProperty.FLOOR.getName(), 2)
                                .build()
                        )
                        .build()
                )
                .build()
);
```

##### Use of the Data Array extension

Send `Observation`s one by one can be network-intensive. The [SensorThings Data Array extension](http://docs.opengeospatial.org/is/15-078r6/15-078r6.html#78) allow you to aggregate multiple `Observation`s together and send them with only one POST request.
Hereafter an example of use of Data Array extension:

```java
// Declare the set of Observation properties that have to be included in the dataArray
final Set<DataArrayValue.Property> properties = new HashSet<>();
properties.add(DataArrayValue.Property.Result);
properties.add(DataArrayValue.Property.PhenomenonTime);
properties.add(DataArrayValue.Property.FeatureOfInterest);

// Create the dataArray associated to the datastream
final DataArrayValue observations = new DataArrayValue(datastream1, properties);

// Add observations
observations.addObservation(MotionObservationBuilder.builder()
        .phenomenonTime(new TimeObject(ZonedDateTime.now()))
        .featureOfInterest(featureOfInterest)
        .result(MotionEventBuilder.builder()
                .subject(MotionSubjectBuilder.builder()
                        .id("1")
                        .build()
                )
                .location(PointFeatureBuilder.builder()
                        .geometry(new Point(new LngLatAlt(10.0, 2.0)))
                        .property(FeatureProperty.BUILDING.getName(), 1)
                        .property(FeatureProperty.FLOOR.getName(), 2)
                        .build()
                )
                .build()
        )
        .build());
observations.addObservation(MotionObservationBuilder.builder()
        .phenomenonTime(new TimeObject(ZonedDateTime.now()))
        .featureOfInterest(featureOfInterest)
        .result(MotionEventBuilder.builder()
                .subject(MotionSubjectBuilder.builder()
                        .id("1")
                        .build()
                )
                .location(PointFeatureBuilder.builder()
                        .geometry(new Point(new LngLatAlt(11.0, 2.0)))
                        .property(FeatureProperty.BUILDING.getName(), 1)
                        .property(FeatureProperty.FLOOR.getName(), 2)
                        .build()
                )
                .build()
        )
        .build());

// Create the DataArrayDocument that gathers all dataArray to be sent to the FUI StoreConnect Sensor API's server
final DataArrayDocument dataArrayDocument = new DataArrayDocument();
dataArrayDocument.addDataArrayValue(observations);

// Finally send the create request to the FUI StoreConnect Sensor API's server 
service.create(dataArrayDocument);
```

##### Initialize `MySensor`'s `Observation`s context

Following the [OGC SensorThings' API entity model](http://docs.opengeospatial.org/is/15-078r6/15-078r6.html#24), any `Observation` is contained into a dedicated `Datastream`.
This `Datastream` represents a sequence of `Observation`s made by a specific `Sensor`, from a specific `Thing` and associated to a specific `ObservedProperty`.
This way, before to publish `MySensor`'s `Observation`s, we need to create the following entities:
- The `MySensor` `Sensor`
- The `MyThing` `Thing`

For the specific `ObservedProperty`(in your case `ObservedProperties#MOTION`), instance is already available from the FUI StoreConnect's Sensor API server. Hence, we do not need to create it, but we need to retrieve its identifier to be able to use it.

Finally, any `Observation` is also associated to a given `FeatureOfInterest`. In the FUI StoreConnect Sensor API's context, this `FeatureOfInterest` represents the venue referential from which the `Observation` has been observed. 

With the FUI StoreConnect Sensor API's client API, all these entities can be created and retrieved as the following:

```java
// Create the access point to the StoreConnnect Sensor API's server
final SensorThingsService service = new SensorThingsService(URI.create("http://localhost:8080/SensorThingsService/v1.0").toURL());

// Create our MyPhone Thing locally
final Thing myPhone = ThingBuilder.builder()
        .name("MyPhone")
        .description("My Phone Thing.")
        .build();

// Finally create our myPhone Thing remotely and set its identifier
service.create(myPhone);

// Create our MySensor Sensor locally
final Sensor mySensor = SensorBuilder.builder()
        .name("MySensor")
        .description("My Sensor.")
        .encodingType(AbstractSensorBuilder.ValueCode.PDF)
        .metadata("http://storeconnect/sensors/mySensor/description")
        .build();

// Finally create our mySensor Sensor remotely and set its identifier
service.create(mySensor);

// Retrieve the reference to the ObservedProperties#MOTION instance
final EntityList<ObservedProperty> observedProperties = service.observedProperties()
        .query()
        .filter("name eq 'Motion'")
        .list();

// We know we have at least one ObservedProperty, because FUI StoreConnect Sensor API server already created it.
final ObservedProperty motion = observedProperties.iterator().next();

// Create the Datastream that will contain our MySensor's Observations and link our previously created entities together
final Datastream datastream = DatastreamBuilder.builder()
        .name("Our MySensor's Datastream")
        .description("Contain sequence of MySensor's Observation")
        .thing(myPhone)
        .sensor(mySensor)
        .observedProperty(motion)
        .build();
service.create(datastream);
```

#### How could be the way to retrieve `MySensor`'s `Observation`s?

```java
// Retrieve any Observation that provides from a Datastream associated with our MySensor
final EntityList<Observation> mySensorObservations = service.observations()
        .query()
        .filter("Datastream/Sensor/name eq 'MySensor'")
        .list();

// Finally print them
mySensorObservations.stream().forEach(System.out::println);
```

### Additional notes

More use case of the API can be found from the [FROST-Client documentation](https://github.com/FraunhoferIOSB/FROST-Client).

#### Specialization of the FROST-Client's entity builders

The FUI StoreConnect Sensor API's client is based on the [FROST-Client](https://github.com/FraunhoferIOSB/FROST-Client) that provides an extensible [builder](https://en.wikipedia.org/wiki/Builder_pattern) for any [OGC SensorThings API's entity](http://docs.opengeospatial.org/is/15-078r6/15-078r6.html#24) to create.
Thereby, the FUI StoreConnect Sensor API's client specializes FROST-Client's entities builders to match with the [FUI StoreConnect Sensor API's entities](https://github.com/StoreConnect/storeconnect-sensors-api#the-storeconnects-sensor-api-data-model):

![FUI StoreConnect Sensor API's entity model](https://raw.githubusercontent.com/StoreConnect/storeconnect-sensors-api/master/resources/storeconnect-sensor-api-data-model.svg?sanitize=true)

This way:

- Non-specialized FUI StoreConnect Sensor API's entities can be created with their original FROST-Client's builders:
    - The [OGC SensorThings API's Thing](http://docs.opengeospatial.org/is/15-078r6/15-078r6.html#25) entity can be constructed with the `de.fraunhofer.iosb.ilt.sta.model.builder.ThingBuilder`
    - The [OGC SensorThings API's HistoricalLocation](http://docs.opengeospatial.org/is/15-078r6/15-078r6.html#27) entity can be constructed with the `de.fraunhofer.iosb.ilt.sta.model.builder.HistoricalLocationBuilder`
    - The [OGC SensorThings API's Sensor](http://docs.opengeospatial.org/is/15-078r6/15-078r6.html#29) entity can be constructed with the `de.fraunhofer.iosb.ilt.sta.model.builder.SensorBuilder`
- Specialized FUI StoreConnect Sensor API's entities can be created with their associated FUI StoreConnect Sensor API's Client's builders:
    - The [FUI StoreConnect Sensor API's Location](https://github.com/StoreConnect/storeconnect-sensors-api#location) entity (specialization of the [OGC SensorThings API's Location](http://docs.opengeospatial.org/is/15-078r6/15-078r6.html#26) entity) can be constructed with the `LocationBuilder`
    - The [FUI StoreConnect Sensor API's Datastream](https://github.com/StoreConnect/storeconnect-sensors-api#datastream) entity (specialization of the [OGC SensorThings API's Datastream](http://docs.opengeospatial.org/is/15-078r6/15-078r6.html#28) entity) can be constructed with the `DatastreamBuilder`
    - The [FUI StoreConnect Sensor API's Observation](https://github.com/StoreConnect/storeconnect-sensors-api#observation) entity (specialization of the [OGC SensorThings API's Observation](http://docs.opengeospatial.org/is/15-078r6/15-078r6.html#31) entity) can be constructed with the `ObservationBuilder`
    - The [FUI StoreConnect Sensor API's FeatureOfInterest](https://github.com/StoreConnect/storeconnect-sensors-api#featureofinterest) entity (specialization of the [OGC SensorThings API's FeatureOfInterest](http://docs.opengeospatial.org/is/15-078r6/15-078r6.html#32) entity) can be constructed with the `FeatureOfInterestBuilder`

In addition, FUI StoreConnect Sensor API's defines one [OGC SensorThings API's ObservedProperty](http://docs.opengeospatial.org/is/15-078r6/15-078r6.html#30) instance by [OGC SensorThings API's Observation](http://docs.opengeospatial.org/is/15-078r6/15-078r6.html#31)'s result type. All FUI StoreConnect Sensor API's ObservedProperties are defined into the `ObservedProperties` class. This way:
- The [FUI StoreConnect Sensor API's Motion ObservedProperty](https://github.com/StoreConnect/storeconnect-sensors-api#observedproperty) is represented by the `ObservedProperties#MOTION` instance

## How to contribute

Feel free to contribute by making a `pull request` following the [contributing](./CONTRIBUTING.md) instructions.

## License

Copyright 2018 Inria Lille

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.