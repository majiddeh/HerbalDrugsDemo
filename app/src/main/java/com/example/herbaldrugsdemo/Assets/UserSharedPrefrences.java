package com.example.herbaldrugsdemo.Assets;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSharedPrefrences {
    private static final String USER_SHARED_PREF_NAME = "user_shared_pref";
    private SharedPreferences sharedPreferences;

    public UserSharedPrefrences(Context context) {
        sharedPreferences = context.getSharedPreferences(USER_SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveUserLoginInfo(String email){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("email",email);
        editor.commit();
    }

    public String getUserLoginInfo(){
        return sharedPreferences.getString("email","ورود/عضویت");
    }

}
