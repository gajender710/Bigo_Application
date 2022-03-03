package com.example.bigo_application.Auth;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.bigo_application.Shared_Prefss;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Authentication {
    Authentication au;
    public static final  String AUTHENTICATION = "auths";
    private static Authentication instance;
    public String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
    public String MAIL_PATTERN = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

    public boolean ok_togo=true;

    public static Authentication getInstance() {
        if(instance==null)
        {
            instance=new Authentication();
        }
        return  instance;
    }

    public TextWatcher Watch(TextInputLayout view)
    {
        android.text.TextWatcher textWatcher =new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    view.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        return textWatcher;
    }


    public String is_mailValid(String mail) {
        if (mail.isEmpty()) {
            return "enter mail";
        }
        Pattern pattern = Pattern.compile(MAIL_PATTERN);
        Matcher matcher = pattern.matcher(mail);
        if (!matcher.matches()) {
            return "Invalid mail format";
        }
        return  null;
    }
    public String is_passlValid(String pass) {

        if (pass.isEmpty()) {
            return "Enter password";
        }
        else if(!is_password_valid(pass))
        {
            return "Incorrect Format";
        }
        return null;
    }
    public boolean is_password_valid(String pass) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(pass);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }
    public String is_userValid(String user) {
        if (user.isEmpty()) {
            return "Enter Username";
        }
        return null;
    }



}
