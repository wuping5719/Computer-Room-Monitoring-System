package com.ouc.dcrms.task.util;

import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.jobclient.JobClient;

import java.util.List;

/**
 * @author WuPing
 */

public class LtsJobSubmitUtil {
    
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
            return;
        }
        if (jobList == null){
            return;
        }
        jobClient.submitJob(jobList);
    }

}
