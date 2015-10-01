# Vert.x Kafka Consumer
This module allows you to bridge messages from Kafka to the Vert.x Event Bus. It allows asynchronous message processing while still maintaining a correct Kafka offset.

It achieves "at-least-once" semantics, all Event Bus messages must be acknowledged by the handler in order to commit the current Kafka offset. This means that your handler on the Event Bus must be able to handle message replays.

When certain limits are reached a commit cycle will happen. A commit cycle waits for all outstanding acknowledgements in order to commit the current Kafka offset. 

Commit cycles will happen on any of the following conditions:

 * `maxUnacknowledged` is reached, meaning that this amount of messages is currently unacknowledged by the Vert.x handlers.
 * `maxUncommited` is reached, meaning that the difference between the last offset that was committed and the current offset is `maxUncommited`
 * The Kafka partition from which the consumer consumes is switched. In order to reduce the amount of commit cycles caused by this condition one should start a consumer per partition.

## Compatibility
- Java 8+
- Vert.x 2.x.x
- Vert.x 3.x.x

## Dependencies

### Dependency Vert.x 2.x.x
### Maven
```xml
<dependency>
    <groupId>com.hubrick.vertx</groupId>
    <artifactId>vertx-kafka-consumer</artifactId>
    <version>1.0.0</version>
    <scope>provided</scope>
</dependency>
```

### Vert.x mod.json
```json
{
    "includes": "com.hubrick.vertx~vertx-kafka-consumer~1.0.0",
}
```

### Dependency Vert.x 3.x.x
### Maven
```xml
<dependency>
    <groupId>com.hubrick.vertx</groupId>
    <artifactId>vertx-kafka-consumer</artifactId>
    <version>2.0.0</version>
</dependency>
```

## How to use

### Configuration:

```JSON
    {
      "groupId" : "groupId",
      "kafkaTopic" : "kafka-topic",
      "vertxAddress" : "message-from-kafka",
      "zk" : "host:port",
      "maxUnacknowledged" : 100,
      "maxUncommitted" : 1000,
      "ackTimeoutMinutes" : 10
    }
```

* `groupId`: Kafka Group Id to use for the Kafka consumer
* `kafkaTopic`: The Kafka topic to subscribe to
* `vertxAddress`: Vert.x event bus address the Kafka messages are relayed to
* `zk`: Zookeeper host and port 
* `maxUnacknowledged`: how many messages from Kafka can be unacknowledged before the module waits for all missing acknowledgements, effectively limiting the amount of messages that are on the Vertx Event Bus at any given time.
* `maxUncommitted`: max offset difference before a commit cycle is run. A commit cycle waits for all unacknowledged messages and then commits the offset to Kafka. Note that when you read from multiple partitions the offset is not continuous and therefore every partition switch causes a commit cycle. For better performance you should start an instance of the module per partition.
* `ackTimeoutMinutes`: the time to wait for all outstanding acknowledgements during a commit cycle. This will just lead to a log message saying how many ACKs are still missing, as the module will wait forever for ACKs in order to achieve at least once semantics.

### Example:

```Java
        vertx.eventBus().registerHandler("message-from-kafka", message -> {
            System.out.println("Got a message from Kafka: " + message.body() );
            message.reply(); // Acknowledge to the Kafka Module that the message has been handled
        });
```

## License
Apache License, Version 2.0
