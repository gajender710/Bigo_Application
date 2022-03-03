package com.example.bigo_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.bigo_application.Auth.Authentication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Sign_In extends AppCompatActivity {
    private TextInputLayout mail, pass, username;
    private Button b, up;
    private TextInputEditText textInputEditText1, textInputEditText2, textInputEditText3;
    private Switch switch2;

    Shared_Prefss sp;
    Authentication au;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();


        sp = Shared_Prefss.getInstance(this);
        au = Authentication.getInstance();
        mail = findViewById(R.id.et_Email);
        pass = findViewById((R.id.et_Pass));
        username = findViewById(R.id.et_Username);

        mail.getEditText().addTextChangedListener(au.Watch(mail));
        pass.getEditText().addTextChangedListener(au.Watch(pass));

        textInputEditText1 = findViewById(R.id.Edittext_mail);
        textInputEditText2 = findViewById(R.id.Edittext_pass);
        textInputEditText3 = findViewById(R.id.Edittext_username);
        switch2 = (Switch) findViewById(R.id.switch2);


        findViewById(R.id.Sign_up_btn).setOnClickListener(view -> {
            startActivity(new Intent(Sign_In.this, Sign_up.class));
        });

        b = findViewById(R.id.Sign_in_btn);
        b.setOnClickListener(this::Submit_data);

        Boolean b = sp.loadBooleanData(sp.SWITCH1);
        if (b) {
            textInputEditText1.setText(sp.loadStringData(sp.TEXT1));
            textInputEditText2.setText(sp.loadStringData(sp.TEXT2));
        } else {
            sp.clearData();
        }
    }

    private void Submit_data(View view) {

        String str_mail_error = au.is_mailValid(mail.getEditText().getText().toString().trim());
        mail.setError(str_mail_error);
        String str_pass_error = au.is_passlValid(pass.getEditText().getText().toString().trim());
        pass.setError(str_pass_error);

        if (mail.getError() != null || pass.getError() != null) return;

/*
        sp.saveData(sp.TEXT1, mail.getEditText().getText().toString());
        sp.saveData(sp.TEXT2, pass.getEditText().getText().toString());
        sp.saveBoolean(sp.SWITCH1, switch2.isChecked());
        String str = mail.getEditText().getText().toString().trim() + "/n" + pass.getEditText().getText().toString().trim();
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
*/

        mAuth.signInWithEmailAndPassword(mail.getEditText().getText().toString().trim(), pass.getEditText().getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Sign_In.this, "login succesful", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Sign_In.this, Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    task.getException().printStackTrace();
                    Toast.makeText(Sign_In.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}