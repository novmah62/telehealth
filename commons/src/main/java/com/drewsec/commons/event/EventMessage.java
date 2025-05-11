package com.drewsec.commons.event;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class EventMessage<T> {
    private final EventType type;
    private final T payload;
    private final LocalDateTime timestamp;
}
