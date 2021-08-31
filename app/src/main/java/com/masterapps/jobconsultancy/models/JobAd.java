package com.masterapps.jobconsultancy.models;


import java.util.List;

public class JobAd  {
    String sJobId, sTitle, sDescription, sQualification, sDateTime;
    List<String> sJobLocation, sApplicant, selectedCandidates;
    String sExp;
    String sSalary;
    String IsExpired;
    Employer employer;

    public JobAd() {
    }

    public JobAd(String sJobId, String sTitle, String sDescription, String sQualification,
                 String sDateTime, List<String> sJobLocation, String sSalary, String isExpired,
                 Employer employer, List<String> sApplicant,List<String> selectedCandidates) {
        this.sJobId = sJobId;
        this.sTitle = sTitle;
        this.sDescription = sDescription;
        this.sQualification = sQualification;
        this.sDateTime = sDateTime;
        this.sJobLocation = sJobLocation;
        this.sSalary = sSalary;
        IsExpired = isExpired;
        this.employer = employer;
        this.sApplicant = sApplicant;
        this.selectedCandidates = selectedCandidates;
    }

    public List<String> getSelectedCandidates() {
        return selectedCandidates;
    }

    public String getsJobId() {
        return sJobId;
    }

    public String getsTitle() {
        return sTitle;
    }

    public String getsDescription() {
        return sDescription;
    }

    public String getsQualification() {
        return sQualification;
    }

    public String getsDateTime() {
        return sDateTime;
    }

    public List<String> getsJobLocation() {
        return sJobLocation;
    }

    public List<String> getsApplicant() {
        return sApplicant;
    }

    public String getsExp() {
        return sExp;
    }

    public String getsSalary() {
        return sSalary;
    }

    public String getExpired() {
        return IsExpired;
    }

    public Employer getEmployer() {
        return employer;
    }
}
