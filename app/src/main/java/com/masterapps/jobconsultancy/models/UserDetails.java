package com.masterapps.jobconsultancy.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class UserDetails {

    final String NAME_KEY = "nameKey";
    final String USER_NAME_KEY = "userNameKey";
    final String EMAIL_KEY = "emailKey";
    final String USER_EXP_KEY = "userExpKey";
    final String USER_ICON_KEY = "UserIconKey";
    final String SALARY_KEY = "SalaryKey";
    public boolean sharedPref;

    String name, emailId, description, gender, contact, whatsappNo, sUserIcon, sAddress, employeeId;
    List<String> languages, appliedJobs,savedJobs,selectedJobs;

    public void setAppliedJobs(List<String> appliedJobs) {
        this.appliedJobs = appliedJobs;
    }

    public List<String> getSavedJobs() {
        return savedJobs;
    }

    public void setSavedJobs(List<String> savedJobs) {
        this.savedJobs = savedJobs;
    }

    public List<String> getSelectedJobs() {
        return selectedJobs;
    }

    public void setSelectedJobs(List<String> selectedJobs) {
        this.selectedJobs = selectedJobs;
    }

    public UserDetails(String name, String emailId, String description, String gender, String contact, String whatsappNo, String sUserIcon, String sAddress, String employeeId,
                       List<String> languages, List<String> appliedJobs, List<String> savedJobs, List<String> selectedJobs) {
        this.name = name;
        this.emailId = emailId;
        this.description = description;
        this.gender = gender;
        this.contact = contact;
        this.whatsappNo = whatsappNo;
        this.sUserIcon = sUserIcon;
        this.sAddress = sAddress;
        this.employeeId = employeeId;
        this.languages = languages;
        this.appliedJobs = appliedJobs;
        this.savedJobs = savedJobs;
        this.selectedJobs = selectedJobs;
    }

    public UserDetails() {

    }

    public void savePrefs(Context context) {

        try {

            SharedPreferences sp = context.getSharedPreferences("com.masterapps.jobconsultancy.users", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            editor.putString(NAME_KEY, name);
            editor.putString(USER_ICON_KEY, sUserIcon);

            Log.d("UserData class ", "user created "+name+" "+name);

            editor.commit();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("UserData class " + 62, "error "+e);
        }
    }

    public boolean isSharedPref(Context context) {
        SharedPreferences sp = context.getSharedPreferences("com.masterapps.jobconsultancy.users", Context.MODE_PRIVATE);
        if(sp.contains(getNAME_KEY())){
            this.sharedPref =  true;
            return sharedPref;
        }else {
            this.sharedPref = false;
            return sharedPref;
        }


    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }


    public String getNAME_KEY() {
        return NAME_KEY;
    }

    public String getUSER_NAME_KEY() {
        return USER_NAME_KEY;
    }

    public String getEMAIL_KEY() {
        return EMAIL_KEY;
    }

    public String getUSER_EXP_KEY() {
        return USER_EXP_KEY;
    }

    public String getUSER_ICON_KEY() {
        return USER_ICON_KEY;
    }

    public String getSALARY_KEY() {
        return SALARY_KEY;
    }

    public void setSharedPref(boolean sharedPref) {
        this.sharedPref = sharedPref;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getWhatsappNo() {
        return whatsappNo;
    }

    public void setWhatsappNo(String whatsappNo) {
        this.whatsappNo = whatsappNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getsUserIcon() {
        return sUserIcon;
    }

    public void setsUserIcon(String sUserIcon) {
        this.sUserIcon = sUserIcon;
    }


    public List<String> getAppliedJobs() {
        return appliedJobs;
    }

    public String getsAddress() {
        return sAddress;
    }


}
