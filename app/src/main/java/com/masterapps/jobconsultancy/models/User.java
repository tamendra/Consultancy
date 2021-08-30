package com.masterapps.jobconsultancy.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class User {

    final String NAME_KEY = "nameKey";
    final String USER_NAME_KEY = "userNameKey";
    final String EMAIL_KEY = "emailKey";
    final String USER_EXP_KEY = "userExpKey";
    final String USER_ICON_KEY = "UserIconKey";
    final String SALARY_KEY = "SalaryKey";
    public boolean sharedPref;

    String emailId, description, gender;
    List<String> languages, rolesInterested;
    String contact;
    String whatsappNo;
    String currentSalary;

    public String name, userName, email, sUserIcon, sExp ;


    public User(String emailId, String description, String gender, List<String> languages, List<String> rolesInterested, String contact, String whatsappNo, String currentSalary, String name, String sUserIcon, String sExp) {
        this.emailId = emailId;
        this.description = description;
        this.gender = gender;
        this.languages = languages;
        this.rolesInterested = rolesInterested;
        this.contact = contact;
        this.whatsappNo = whatsappNo;
        this.currentSalary = currentSalary;
        this.name = name;
        this.sUserIcon = sUserIcon;
        this.sExp = sExp;
    }

    public User() {

    }


    public void savePrefs(Context context) {

        try {

            SharedPreferences sp = context.getSharedPreferences("com.masterapps.jobconsultancy.users", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            editor.putString(NAME_KEY, name);
            editor.putString(USER_NAME_KEY, userName);
            editor.putString(USER_ICON_KEY, sUserIcon);
            editor.putString(USER_EXP_KEY,  sExp);
            editor.putString(SALARY_KEY,  currentSalary+"");
            editor.putString(EMAIL_KEY, email);

            Log.d("UserData class ", "user created "+name+" "+userName);

            editor.commit();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("UserData class " + 62, "error "+e);
        }
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

    public List<String> getRolesInterested() {
        return rolesInterested;
    }

    public void setRolesInterested(List<String> rolesInterested) {
        this.rolesInterested = rolesInterested;
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

    public String getCurrentSalary() {
        return currentSalary;
    }

    public void setCurrentSalary(String currentSalary) {
        this.currentSalary = currentSalary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getsUserIcon() {
        return sUserIcon;
    }

    public void setsUserIcon(String sUserIcon) {
        this.sUserIcon = sUserIcon;
    }

    public String getsExp() {
        return sExp;
    }

    public void setsExp(String sExp) {
        this.sExp = sExp;
    }
}
