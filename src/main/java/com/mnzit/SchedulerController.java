package com.mnzit;

import com.mnzit.dto.CreateJob;
import com.mnzit.scheduler.EventJob;
import com.mnzit.service.SchedulerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;

import java.time.Duration;
import java.util.UUID;

@Path("/schedule")
public class SchedulerController {

    @Inject
    SchedulerService schedulerService;

    @GET
    @Path("{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String schedule(@PathParam("name") String name) {
        CreateJob createJob = new CreateJob();
        createJob.setJobClass(EventJob.class);
        createJob.setDuration(Duration.ofSeconds(15));
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
