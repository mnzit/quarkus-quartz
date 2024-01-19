package com.mnzit.producer;

import com.mnzit.event.EventController;
import com.mnzit.manager.ScheduledEventManager;
import io.smallrye.common.annotation.Blocking;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;
import org.quartz.JobDataMap;

import java.util.List;

/**
 * @author Manjit Shakya
 */
@ApplicationScoped
public class EventJobConsumer {

    @Inject
    Logger log;

    @Inject
    ScheduledEventManager scheduledEventManager;

    @Inject
    Jsonb jsonb;

    @PostConstruct
    public void init() {
        scheduledEventManager.init(List.of(
                EventController.class
        ));
    }


    @Incoming("event-job")
    @Blocking
    public void getEvent(String event) {
        log.info("event received: " + event);
        try {
            JobDataMap jobDataMap = jsonb.fromJson(event, JobDataMap.class);
            scheduledEventManager.invoke(jobDataMap);
        } catch (Exception ex) {
            log.error("Exception: " + ex.getMessage());
        }
    }
}
