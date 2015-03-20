/**
 * Copyright (C) 2015 Etaia AS (oss@hubrick.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hubrick.vertx.kafka;

import org.vertx.java.busmods.BusModBase;
import org.vertx.java.core.eventbus.Message;

/**
 * Vert.x Module to read from a Kafka Topic.
 *
 * Created by Marcus Thiesen (mt@hubrick.com) on 04.03.15.
 */
public class KafkaMessageConsumer extends BusModBase {

    public static final int DEFAULT_ZOOKEEPER_TIMEOUT_MS = 100000;
    private VertxKafkaConsumer consumer;
    private KafkaConfiguration configuration;

    @Override
    public void start() {
        super.start();

        configuration = KafkaConfiguration.create(
                getMandatoryStringConfig(KafkaConfiguration.KEY_GROUP_ID),
                getMandatoryStringConfig(KafkaConfiguration.KEY_KAFKA_TOPIC),
                getMandatoryStringConfig(KafkaConfiguration.KEY_VERTX_ADDRESS),
                getMandatoryStringConfig(KafkaConfiguration.KEY_ZOOKEEPER),
                getOptionalIntConfig(KafkaConfiguration.KEY_ZOOKEPER_TIMEOUT_MS, DEFAULT_ZOOKEEPER_TIMEOUT_MS),
                getOptionalIntConfig(KafkaConfiguration.KEY_MAX_UNACKNOWLEDGED, 100 ),
                getOptionalLongConfig(KafkaConfiguration.KEY_MAX_UNCOMMITTED_OFFSETS, 1000 ),
                getOptionalLongConfig(KafkaConfiguration.KEY_ACK_TIMEOUT_MINUTES, 10 ));

        consumer = VertxKafkaConsumer.create( configuration, this::handler );

        consumer.start();
    }

    private void handler( final String vertxAddress, final String jsonMessage, final Runnable ack ) {
        vertx.eventBus().send(vertxAddress, jsonMessage, (Message<String> message) -> {
            ack.run();
        } );
    }

    @Override
    public void stop() {
        if ( consumer != null ) {
            consumer.stop();
        }
        super.stop();
    }
}
