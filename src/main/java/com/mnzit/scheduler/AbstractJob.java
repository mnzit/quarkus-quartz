package com.mnzit.scheduler;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author Manjit Shakya
 */
public abstract class AbstractJob implements Job {

    public JobDataMap getJobData(JobExecutionContext jobExecutionContext) {
        return jobExecutionContext
                .getTrigger()
                .getJobDataMap();
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context
                .getTrigger()
                .getJobDataMap();
        execute(jobDataMap);
    }

    public abstract void execute(JobDataMap jobDataMap);
}
