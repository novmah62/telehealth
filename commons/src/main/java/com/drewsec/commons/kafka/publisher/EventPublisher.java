package com.drewsec.commons.kafka.publisher;

public interface EventPublisher {

    <T> void publish(String topic, String key, T payload);

}