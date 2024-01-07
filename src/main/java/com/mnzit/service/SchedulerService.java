package com.mnzit.service;

import com.mnzit.dto.CreateJob;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@ApplicationScoped
public class SchedulerService {

    @Inject
    Scheduler scheduler;

    @Inject
    Logger log;

    public void scheduleJob(CreateJob createJob) throws SchedulerException {

        JobDetail job = JobBuilder
                .newJob(createJob.getJobClass())
                .storeDurably(true)
                .withIdentity(createJob.getJobName(), createJob.getGroupName())
                .withDescription(createJob.getJobDescription())
                .usingJobData(createJob.getJobDataMap())
                .build();

        LocalDateTime currentTime = LocalDateTime.now(ZoneId.systemDefault());
        log.debug("currentTime: " + currentTime);
        LocalDateTime triggerDate = currentTime.plus(createJob.getDuration());
        log.debug("triggerDate: " + triggerDate);

        Date date = Date.from(triggerDate.atZone(createJob.getZoneId()).toInstant());
        log.debug("date scheduled: " + date);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(createJob.getJobName(), createJob.getJobDescription())
                .withSchedule(
                        SimpleScheduleBuilder
                                .simpleSchedule()
                                .withMisfireHandlingInstructionNextWithExistingCount())
                .startAt(date)
                .usingJobData(createJob.getJobDataMap())
                .build();

        scheduler.scheduleJob(job, trigger);
    }
}