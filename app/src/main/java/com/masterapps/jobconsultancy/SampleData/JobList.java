package com.masterapps.jobconsultancy.SampleData;

import com.masterapps.jobconsultancy.models.Application;
import com.masterapps.jobconsultancy.models.Job;

import java.util.ArrayList;
import java.util.List;

public class JobList {

    List<Job> jobs;
    List<Application> applications;
    int noOfJobs;
    static String JOB_ROLE;


    public static Job myJobMLM(){

        List<String> locations = new ArrayList<>(10);
        locations.add("WFH");
        Job job = new Job(
                "1000",
                "App testing and Bug finding",
                "Master Apps",
                "1000",
                "Testing this app and sharing with your peer-mates and find bugs and app-crash\n users with high referral will be awarded",
                "10th pass",
                locations,
                "master.apps.2021@gmail.com",
                "31/12/2021 23:59",0,00000000,5000
        );
        return job;
    }

    public JobList(int noOfJobs, String JOB_ROLE) {
        this.noOfJobs = noOfJobs;
        this.JOB_ROLE = JOB_ROLE;
        this.jobs = new ArrayList<>(10);

    }

    public JobList(int noOfJobs, String JOB_ROLE, List<Job> jobs) {
        this.noOfJobs = noOfJobs;
        this.JOB_ROLE = JOB_ROLE;

        this.jobs = new ArrayList<>(10);
        this.applications = new ArrayList<>(10);

        this.jobs = getJobs();
        this.applications = getApplications();

    }

    public List<Application> getApplications() {

        for(int i=0; i<this.noOfJobs;i++){
            Application application = new Application(jobs.get(i).getsJobId(),"thisUserId");
            applications.add(application);
        }

        return applications;

    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public List<Job> getJobs() {

        List<String> locations = new ArrayList<>(10);
        locations.add("WFH");
        for (int i = 0; i < noOfJobs; i++) {
            Job job = new Job(
                    JOB_ROLE+i,
                    JOB_ROLE+" no "+i,
                    "com.masterapps.jobconsultancy","ID",
                    "Developed an Android app based on traditional Indian board game TIRI PASSA using Firebase\n" + "cloud store and Realtime database. The app includes 3 modes - offline mode, online mode with\n" + "friends and online mode with random players",
                    "12th pass",
                    locations,
                    "s@mail.com",
                    "26/08/2021 09:00",0,98898989,5000
                    );

            jobs.add(job);
        }

        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
