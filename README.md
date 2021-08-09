# Java
[![Test and Deploy](https://github.com/edge-ml/java/actions/workflows/deploy.yml/badge.svg)](https://github.com/edge-ml/java/actions/workflows/deploy.yml)
![Maven Central](https://img.shields.io/maven-central/v/edu.teco.explorer/ExplorerJava?color=%2348c653)

Java library for https://github.com/edge-ml. 
Can be used to upload datasets as whole or incrementally. 
Written in Java. Can be used in Android projects.

## How to install
The library can be found in Maven Central.

### Gradle
1. Add mavenCentral to you repositories if it is not alreaedy there
```gradle
repositories {
  mavenCentral()
}
```

2. Import the library
```gradle
dependencies {
  implementation 'edu.teco.explorer:ExplorerJava:${VERSION}
}
```

### Maven
Include the library as a dependency in pom.xml
```xml
<dependencies>
  <dependency>
    <groupId>edu.teco.explorer</groupId>
    <artifactId>ExplorerJava</artifactId>
    <version>${VERSION}</version>
  </dependency>
</dependencies>
```


## How to use

#### Upload datasets as a whole

```java
Recorder recorder = new Recorder("explorerBackendUrl", "deviceApiKey");
boolean res = recorder.sendDataset(JSONObject); // Dataset as JSONObject
```

#### Upload datasets in increments with custom timestamps

```java
Recorder recorder = new Recorder("explorerBackendUrl", "deviceApiKey");
try {
  IncrementalRecorder incRecorder = recorder.getIncrementalDataset("datasetName", false); // false to use custom timestamps

  // time should be a unix timestamp
  incRecorder.addDataPoint(1595506316000L, "accX", 123);

  // This will throw an UnsupportedOperationException because no timestamp was provided
  incRecorder.addDataPoint("accX", 124);

  // Tells the libarary that all data has been recorded
  // Uploads all remaining datapoints to the server
  incRecorder.onComplete();
}
} catch (Exception e) {
    e.printStackTrace();
}
```

#### Upload datasets in increments with timestamps from the device

```java
Recorder recorder = new Recorder("explorerBackendUrl", "deviceApiKey");
try {
  IncrementalRecorder incRecorder = recorder.getIncrementalDataset("datasetName", true); // true to use deviceTime


  incRecorder.addDataPoint("accX", 123);

  // This will throw an UnsupportedOperationException because a timestamp was provided
  incRecorder.addDataPoint(1595506316000L, "accX", 123);

  // Wait until all values have been send
  incRecorder.onComplete();
} catch (Exception e) {
    e.printStackTrace();
}
```
