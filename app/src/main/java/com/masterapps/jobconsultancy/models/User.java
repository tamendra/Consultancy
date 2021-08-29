package com.masterapps.jobconsultancy.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    final String NAME_KEY = "nameKey";
    final String USER_NAME_KEY = "userNameKey";
    final String EMAIL_KEY = "emailKey";
    final String USER_EXP_KEY = "userExpKey";
    final String USER_ICON_KEY = "UserIconKey";
    final String KAURI_KEY = "KauriKey";

    String emailId, description, gender, rolesInterested, languages[];
    int contact, whatsappNo, currentSalary;

    public String name, userName, email, sUserIcon, sExp, sKauri ;

    public User(String name, String userName, String email, String sUserIcon, String sExp, String sKauri) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.sUserIcon = sUserIcon;
        this.sExp = sExp;
        this.sKauri = sKauri;
    }

    public User() {

    }


    public void savePrefs(Context context) {

        try {

            SharedPreferences sp = context.getSharedPreferences("tiripassa", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            editor.putString(NAME_KEY, name);
            editor.putString(USER_NAME_KEY, userName);
            editor.putString(USER_ICON_KEY, sUserIcon);
            editor.putString(USER_EXP_KEY,  sExp);
            editor.putString(KAURI_KEY,  sKauri);
            editor.putString(EMAIL_KEY, email);

            Log.d("UserData class ", "user created "+name+" "+userName);

            editor.commit();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("UserData class " + 62, "error "+e);
        }
    }


}
