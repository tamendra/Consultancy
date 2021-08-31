package com.masterapps.jobconsultancy.models;

public class Application extends Object{

    String sApplicationId, sJobId, sApplicantId, sDateApplied, sStatus;

    public Application(String sApplicationId, String sJobId, String sApplicantId, String sDateApplied, String sStatus) {
        this.sApplicationId = sApplicationId;
        this.sJobId = sJobId;
        this.sApplicantId = sApplicantId;
        this.sDateApplied = sDateApplied;
        this.sStatus = sStatus;
    }

    public String getsApplicationId() {
        return sApplicationId;
    }

    public void setsApplicationId(String sApplicationId) {
        this.sApplicationId = sApplicationId;
    }

    public String getsJobId() {
        return sJobId;
    }

    public void setsJobId(String sJobId) {
        this.sJobId = sJobId;
    }

    public String getsApplicantId() {
        return sApplicantId;
    }

    public void setsApplicantId(String sApplicantId) {
        this.sApplicantId = sApplicantId;
    }

    public String getsDateApplied() {
        return sDateApplied;
    }

    public void setsDateApplied(String sDateApplied) {
        this.sDateApplied = sDateApplied;
    }

    public String getsStatus() {
        return sStatus;
    }

    public void setsStatus(String sStatus) {
        this.sStatus = sStatus;
    }
}
