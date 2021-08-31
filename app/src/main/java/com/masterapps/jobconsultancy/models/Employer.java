package com.masterapps.jobconsultancy.models;

public class Employer {
    String employerId, name, address, email, contact;

    public String getEmployerId() {
        return employerId;
    }

    public Employer() {
    }

    public Employer(String employerId, String name, String address, String email, String contact) {
        this.employerId = employerId;
        this.name = name;
        this.address = address;
        this.email = email;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }
}
