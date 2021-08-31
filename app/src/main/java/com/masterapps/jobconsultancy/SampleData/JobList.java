package com.masterapps.jobconsultancy.SampleData;

import com.masterapps.jobconsultancy.models.Application;
import com.masterapps.jobconsultancy.models.JobAd;

import java.util.ArrayList;
import java.util.List;

public class JobList {

    List<JobAd> jobAds;
    List<Application> applications;
    int noOfJobs;
    static String JOB_ROLE;


    public JobList(int noOfJobs, String JOB_ROLE, List<JobAd> jobAds) {
        this.noOfJobs = noOfJobs;
        this.JOB_ROLE = JOB_ROLE;

        this.jobAds = new ArrayList<>(10);
        this.applications = new ArrayList<>(10);

        this.jobAds = getJobs();
        this.applications = getApplications();

    }

    public List<Application> getApplications() {

        for(int i=0; i<this.noOfJobs;i++){
        }

        return applications;

    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public List<JobAd> getJobs() {

            jobAds.add(new JobAd());

        return jobAds;
    }

    public void setJobs(List<JobAd> jobAds) {
        this.jobAds = jobAds;
    }
}
