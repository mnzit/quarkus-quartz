package com.mnzit.producer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

/**
 * @author Manjit Shakya
 */
@ApplicationScoped
public class EventJobProducer {

    @Inject @Channel("event-job-out")
    Emitter<String> searchEvents;
    public Emitter<String> getEventJob() {
        return searchEvents;
    }

}
