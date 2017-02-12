package com.ouc.dcrms.task.util;

import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.jobclient.JobClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 

import java.util.List;

/**
 * @author WuPing
 */

public class LtsJobSubmitUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(LtsJobSubmitUtil.class);
    
    private JobClient<?, ?> jobClient;
    private List<Job> jobList;
 
    public JobClient<?, ?> getJobClient() {
        return jobClient;
    }
 
    public void setJobClient(JobClient<?, ?> jobClient) {
        this.jobClient = jobClient;
    }
 
    public List<Job> getJobList() {
        return jobList;
    }
 
    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }
 
    public void initSubmitJobs(){
        if (jobClient == null){
            LOGGER.warn("jobClient is null,please set up!");
            return;
        }
        if (jobList == null){
            LOGGER.warn("not set job,please set up!");
            return;
        }
        jobClient.submitJob(jobList);
        LOGGER.info("job submit success!");
    }

}
