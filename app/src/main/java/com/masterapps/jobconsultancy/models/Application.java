package com.masterapps.jobconsultancy.models;

public class Application extends Object{

    String sApplicationId, sJobId, sApplicantId, sDateApplied;
    int sStatus;

    public Application(String sJobId, String sApplicantId) {
        this.sJobId = sJobId;
        this.sApplicationId = createApplicationID();
        this.sDateApplied = createTimeApplied();
        this.sStatus = 0;
        this.sApplicantId = sApplicantId;
        // 0 for open, 1 for closed, 2 for process ongoing, 3 selected , 4 saved but not Applied
    }

    public int getsStatus() {
        return sStatus;
    }

    private String createTimeApplied() {
        return "05/09/2021 09:30";
    }

    private String createApplicationID() {
        return "ApplicationId";
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

    public String getsDateApplied() {
        return sDateApplied;
    }

    public void setsDateApplied(String sDateApplied) {
        this.sDateApplied = sDateApplied;
    }
}
