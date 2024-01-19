package com.mnzit.event;

import com.mnzit.annotation.ScheduledEvent;
import com.mnzit.annotation.ScheduledEventController;
import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.quartz.JobDataMap;

/**
 * @author Manjit Shakya
 */
@Unremovable
@ApplicationScoped
@ScheduledEventController
public class EventController {

    @Inject
    Logger log;

    @ScheduledEvent(name = "START_EVENT")
    public void runEvent(JobDataMap jobDataMap) {
        log.info("Start event called");
    }

    @ScheduledEvent(name = "END_EVENT")
    public void endEvent(JobDataMap jobDataMap) {
        log.info("End event called");
    }
}
