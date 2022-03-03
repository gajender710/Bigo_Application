package com.example.bigo_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bigo_application.Auth.Authentication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Sign_up extends AppCompatActivity {
    private TextInputLayout mail, pass, username;
    private Button b;

    private TextInputEditText textInputEditText1, textInputEditText2, textInputEditText3;
    private Switch switch1;

    //Shared_Prefss sp;
    Authentication au;
    FirebaseAuth mAuth;
    // public static final String SHARED_PREFS = "sharedPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        // sp = Shared_Prefss.getInstance(this);
        au = Authentication.getInstance();
        mail = findViewById(R.id.et_Email);
        pass = findViewById((R.id.et_Pass));
        username = findViewById(R.id.et_Username);

        mail.getEditText().addTextChangedListener(au.Watch(mail));
        pass.getEditText().addTextChangedListener(au.Watch(pass));
        username.getEditText().addTextChangedListener(au.Watch(username));

        textInputEditText1 = findViewById(R.id.Edittext_mail);
        textInputEditText2 = findViewById(R.id.Edittext_pass);
        textInputEditText3 = findViewById(R.id.Edittext_username);
        switch1 = (Switch) findViewById(R.id.switch1);

        b = findViewById(R.id.Sign_up);
        b.setOnClickListener(this::Submit_data);

        /*
        Boolean b = sp.loadBooleanData(sp.SWITCH1);
        if (b) {
            textInputEditText1.setText(sp.loadStringData(sp.TEXT1));
            textInputEditText2.setText(sp.loadStringData(sp.TEXT2));
            textInputEditText3.setText(sp.loadStringData(sp.TEXT3));
        }
        else
        {
            sp.clearData();
        }*/
    }

    private void Submit_data(View view) {

        String str_mail = au.is_mailValid(mail.getEditText().getText().toString().trim());
        mail.setError(str_mail);
        String str_pass = au.is_passlValid(pass.getEditText().getText().toString().trim());
        pass.setError(str_pass);
        String str_user = au.is_userValid(username.getEditText().getText().toString().trim());
        username.setError(str_user);


        if (mail.getError() != null || pass.getError() != null || username.getError() != null)
            return;

        /*
        sp.saveData(sp.TEXT1, mail.getEditText().getText().toString());
        sp.saveData(sp.TEXT2, pass.getEditText().getText().toString());
        sp.saveData(sp.TEXT3, username.getEditText().getText().toString());
        sp.saveBoolean(sp.SWITCH1, switch1.isChecked());*/

        mAuth.createUserWithEmailAndPassword(mail.getEditText().getText().toString().trim(), pass.getEditText().getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    saveData();
                    Toast.makeText(Sign_up.this, "Sign up succesful", Toast.LENGTH_LONG).show();

                } else {
                    task.getException().printStackTrace();
                    Toast.makeText(Sign_up.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        String str = mail.getEditText().getText().toString().trim() + "/n" + pass.getEditText().getText().toString().trim();
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();

        // startActivity(new Intent(Sign_up.this, Home.class));

    }

    private void saveData() {
        FirebaseFirestore fs = FirebaseFirestore.getInstance();
        Map<String, Object> person = new HashMap<>();
        person.put("Email", mail.getEditText().getText().toString().trim());
        person.put("Username", username.getEditText().getText().toString().trim());
        person.put("Password", pass.getEditText().getText().toString().trim());
        fs.collection("UserData").document(mAuth.getCurrentUser().getUid()).set(person)
                .addOnSuccessListener(auth -> {
                    Intent intent = new Intent(Sign_up.this, Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
            Toast.makeText(Sign_up.this, "Details  uploaded", Toast.LENGTH_LONG).show();
        });

    }

}