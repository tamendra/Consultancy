package com.masterapps.jobconsultancy.models;


import java.util.List;
import java.util.Objects;

public class Job extends Object {
    String sJobId, sTitle, sEmployerName, sEmployerId, sDescription, sQualification, sEmail, sDateTime;
    List<String> sJobLocatoin;
    int sExp, sContact, sSalary;
    Boolean IsApplied, IsExpired;

    public Job() {
    }

    public Job (String sJobId, String sTitle, String sEmployerName, String sEmployerId,
                String sDescription, String sQualification, List<String> sJobLocatoin, String sEmail,
                String sDateTime, int sExp, int sContact, int sSalary)

    {
        if(sJobId!=null||!sJobId.isEmpty()){
            this.sJobId = sJobId;
        }
        if(sTitle!=null||!sTitle.isEmpty()) {
            this.sTitle = sTitle;
        }

        if(sEmployerName!=null||!sEmployerName.isEmpty()) {
            this.sEmployerName = sEmployerName;
        }
        if(sEmployerId!=null||!sEmployerId.isEmpty()) {
            this.sEmployerId = sEmployerId;
        }
        if(sDescription!=null||!sDescription.isEmpty()) {
            this.sDescription = sDescription;
        }
        if(sQualification!=null||!sQualification.isEmpty()) {
            this.sQualification = sQualification;
        }
        if(sJobLocatoin!=null|| sJobLocatoin.size() !=0) {
            this.sJobLocatoin = sJobLocatoin;
        }
        if(sEmail!=null||!sEmail.isEmpty()) {
            this.sEmail = sEmail;
        }
        if(sDateTime!=null||!sDateTime.isEmpty()) {
            this.sDateTime = sDateTime;
        }
        if(!(sExp<0)) {
            this.sExp = sExp;
        }

        if(!(sContact<=0)) {
            this.sContact = sContact;
        }
        if(!(sSalary<=0)) {
            this.sSalary = sSalary;
        }
    }

    public String getsJobId() {
        return sJobId;
    }

    public void setsJobId(String sJobId) {
        this.sJobId = sJobId;
    }

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public String getsEmployerName() {
        return sEmployerName;
    }

    public void setsEmployerName(String sEmployerName) {
        this.sEmployerName = sEmployerName;
    }

    public String getsEmployerId() {
        return sEmployerId;
    }

    public void setsEmployerId(String sEmployerId) {
        this.sEmployerId = sEmployerId;
    }

    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription;
    }

    public String getsQualification() {
        return sQualification;
    }

    public void setsQualification(String sQualification) {
        this.sQualification = sQualification;
    }

    public List<String> getsJobLocatoin() {
        return sJobLocatoin;
    }

    public void setsJobLocatoin(List<String> sJobLocatoin) {
        this.sJobLocatoin = sJobLocatoin;
    }

    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public String getsDateTime() {
        return sDateTime;
    }

    public void setsDateTime(String sDateTime) {
        this.sDateTime = sDateTime;
    }

    public int getsExp() {
        return sExp;
    }

    public void setsExp(int sExp) {
        this.sExp = sExp;
    }

    public int getsContact() {
        return sContact;
    }

    public void setsContact(int sContact) {
        this.sContact = sContact;
    }

    public int getsSalary() {
        return sSalary;
    }

    public void setsSalary(int sSalary) {
        this.sSalary = sSalary;
    }
}
