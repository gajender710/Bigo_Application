package com.example.bigo_application;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.TransitionManager;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this.getApplicationContext());

        mAuth=FirebaseAuth.getInstance();
        firebaseUser=mAuth.getCurrentUser();
        img=findViewById(R.id.main_activity_start_logo);

        Shared_Prefss sh;
        Boolean flag=false;
        sh = new Shared_Prefss(this);
        final ConstraintLayout start_Layout = findViewById(R.id.constraint_splash_start);
        final ConstraintSet cons_End = new ConstraintSet();
        cons_End.clone(this, R.layout.activity_main1);

        Glide.with(this).load(R.drawable.insta_img).override(250,250).diskCacheStrategy(DiskCacheStrategy.ALL).into(img);
        new Handler().postDelayed(() -> {
            Log.d(TAG, "run: hit");
            TransitionManager.beginDelayedTransition(start_Layout);
            cons_End.applyTo(start_Layout);

            new Handler().postDelayed(() -> {

                if (firebaseUser!=null){
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
                else{Intent intent = new Intent(MainActivity.this, Sign_In.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);}
            },3000);
        }, 50);
    }

}