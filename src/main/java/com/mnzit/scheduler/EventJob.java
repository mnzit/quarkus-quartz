package com.mnzit.scheduler;

import com.mnzit.manager.ScheduledEventManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.quartz.JobDataMap;

/**
 * Triggers a kafka message in a scheduled timeframe.
 *
 * @author Manjit Shakya
 */
@ApplicationScoped
public class EventJob extends AbstractJob {


    @Inject
    Logger log;

    @Inject
    ScheduledEventManager scheduledEventManager;

    public void execute(JobDataMap jobDataMap) {
        try {
            scheduledEventManager.invoke(jobDataMap);
        } catch (Exception e) {
            log.error("Exception: " + e.getMessage());
        }
    }
}
