package com.mnzit.scheduler;

import com.mnzit.producer.EventJobProducer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
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

    @Inject
    Jsonb jsonb;

    public void execute(JobDataMap jobDataMap) {
        String request = jsonb.toJson(jobDataMap);
        eventJobProducer.getEventJob().send(request);
    }
}
