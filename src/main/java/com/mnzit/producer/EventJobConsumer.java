package com.mnzit.producer;

import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

/**
 * @author Manjit Shakya
 */
@ApplicationScoped
public class EventJobConsumer {

    @Inject
    Logger log;

    @Incoming("event-job")
    @Blocking
    public void getEvent(String event) {
        log.info("event received: " + event);
    }
}
