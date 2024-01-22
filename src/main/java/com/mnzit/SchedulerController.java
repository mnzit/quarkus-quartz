package com.mnzit;

import com.mnzit.dto.CreateJob;
import com.mnzit.event.EventController;
import com.mnzit.manager.ScheduledEventManager;
import com.mnzit.scheduler.EventJob;
import com.mnzit.service.SchedulerService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Path("/schedule")
public class SchedulerController {

    @Inject
    SchedulerService schedulerService;

    @Inject
    ScheduledEventManager scheduledEventManager;

    @PostConstruct
    public void init() {
        scheduledEventManager.init(List.of(EventController.class));
    }

    @GET
    @Path("{name}/{seconds}")
    @Produces(MediaType.TEXT_PLAIN)
    public String schedule(@PathParam("name") String name, @PathParam("seconds") Long seconds) {
        CreateJob createJob = new CreateJob();
        createJob.setJobClass(EventJob.class);
        createJob.setDuration(Duration.ofSeconds(seconds));
        createJob.setJobName(UUID.randomUUID().toString());
        createJob.setJobDescription("Test Job Description");
        createJob.setGroupName("TEST");
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("name", name);
        createJob.setJobDataMap(jobDataMap);

        try {
            schedulerService.scheduleJob(createJob);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

        return "SCHEDULED";
    }
}
