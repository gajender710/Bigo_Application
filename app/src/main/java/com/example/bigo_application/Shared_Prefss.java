package com.example.bigo_application;

import android.content.Context;
import android.content.SharedPreferences;


public class Shared_Prefss {
    private final SharedPreferences s;
    public static final  String SHARED_PREFS = "sharedPrefs";
    private static Shared_Prefss instance;

    public static final String TEXT1 = "text1";
    public static final String TEXT2 = "text2";
    public static final String TEXT3 = "text3";
    public static final String SWITCH1 = "switch1";

    public static final Boolean ok_Togo = false;


    public static Shared_Prefss getInstance(Context context){
        if(instance==null)
        {
            instance=new Shared_Prefss(context.getApplicationContext());
        }
        return  instance;
    }

    public Shared_Prefss(Context context) {
       s= context.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
    }


    public void saveData(String savedVariableName, String Value) {
        SharedPreferences.Editor editor=s.edit();
        editor.putString(savedVariableName,Value);
        editor.apply();
        //Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }
    public void saveBoolean(String savedVariableName, boolean value) {
        SharedPreferences.Editor editor = s.edit();
        editor.putBoolean(savedVariableName, value);
        editor.apply();
    }

    public String loadStringData(String savedVariableName) {
        return s.getString(savedVariableName, null);
    }

    public boolean loadBooleanData(String savedVariableName) {
        return s.getBoolean(savedVariableName, false);
    }

    public void clearData()
    {
        SharedPreferences.Editor editor= s.edit();
        editor.clear();
        editor.apply();
    }

}
