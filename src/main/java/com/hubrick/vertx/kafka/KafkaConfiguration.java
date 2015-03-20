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

/**
 * Configuration Options for the Kafka Consumer.
 *
 * See https://github.com/hubrick/vertx-kafka-consumer for details.
 *
 * Created by Marcus Thiesen (mt@hubrick.com) on 04.03.15.
 */
class KafkaConfiguration {

    public static final String KEY_GROUP_ID = "groupId";
    public static final String KEY_KAFKA_TOPIC = "kafkaTopic";
    public static final String KEY_VERTX_ADDRESS = "vertxAddress";
    public static final String KEY_ZOOKEEPER = "zk";
    public static final String KEY_ZOOKEPER_TIMEOUT_MS = "zookeperTimeout";
    public static final String KEY_MAX_UNACKNOWLEDGED = "maxUnacknowledged";
    public static final String KEY_MAX_UNCOMMITTED_OFFSETS = "maxUncommitted";
    public static final String KEY_ACK_TIMEOUT_MINUTES = "ackTimeoutMinutes";

    private final String groupId;
    private final String kafkaTopic;
    private final String vertxAddress;
    private final String zookeeper;
    private final int zookeeperTimeout;
    private final int maxUnacknowledged;
    private final long maxUncommitedOffsets;
    private final long ackTimeoutMinutes;

    private KafkaConfiguration( final String groupId,
                                final String kafkaTopic,
                                final String vertxTopic,
                                final String zookeeper,
                                final int zookeeperTimeout,
                                final int maxUnacknowledged,
                                final long maxUncommittedOffset,
                                final long ackTimeoutMinutes ) {
        this.groupId = groupId;
        this.kafkaTopic = kafkaTopic;
        this.vertxAddress = vertxTopic;
        this.zookeeper = zookeeper;
        this.zookeeperTimeout = zookeeperTimeout;
        this.maxUnacknowledged = maxUnacknowledged;
        this.maxUncommitedOffsets = maxUncommittedOffset;
        this.ackTimeoutMinutes = ackTimeoutMinutes;
    }

    public static KafkaConfiguration create( final String groupId,
                                             final String kafkaTopic,
                                             final String vertxTopic,
                                             final String zookeeper,
                                             final int zookeeperTimeout,
                                             final int maxUnacknowledged,
                                             final long maxUncommittedOffsets,
                                             final long ackTimeoutMinutes ) {
        return new KafkaConfiguration( groupId,
                                       kafkaTopic,
                                       vertxTopic,
                                       zookeeper,
                                       zookeeperTimeout,
                                       maxUnacknowledged,
                                       maxUncommittedOffsets,
                                       ackTimeoutMinutes );
    }

    public String getGroupId() {
        return groupId;
    }

    public String getKafkaTopic() {
        return kafkaTopic;
    }

    public String getVertxAddress() {
        return vertxAddress;
    }

    public String getZookeeper() {
        return zookeeper;
    }

    public int getZookeeperTimeout() {
        return zookeeperTimeout;
    }

    public int getMaxUnacknowledged() {
        return maxUnacknowledged;
    }

    public long getMaxUncommitedOffsets() {
        return maxUncommitedOffsets;
    }

    public long getAckTimeoutMinutes() {
        return ackTimeoutMinutes;
    }
}
