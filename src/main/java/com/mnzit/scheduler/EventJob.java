package com.mnzit.scheduler;

import com.mnzit.producer.EventJobProducer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quartz.JobDataMap;

/**
 * Triggers a kafka message in a scheduled timeframe.
 *
 * @author Manjit Shakya
 */
@ApplicationScoped
public class EventJob extends AbstractJob {


    @Inject
    EventJobProducer eventJobProducer;

    public void execute(JobDataMap jobDataMap) {
        String name = (String) jobDataMap.get("name");
        eventJobProducer.getEventJob().send(name);
    }
}
