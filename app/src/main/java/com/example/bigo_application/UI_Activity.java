package com.example.bigo_application;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

public class UI_Activity extends AppCompatActivity {

    GenericDraweeHierarchy hierarchy;
    RoundingParams roundingParams;
    SimpleDraweeView ui_Image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this.getApplicationContext());
        setContentView(R.layout.activity_ui);

        //hierarchy=GenericDraweeHierarchyBuilder.newInstance(getResources()).setRoundingParams(roundingParams);

        ui_Image.setHierarchy(hierarchy);
        ui_Image=findViewById(R.id.ui_Profile_pic);
        ui_Image.setImageURI("https://i.picsum.photos/id/377/400/400.jpg?hmac=91CZtj-HJKTbsbYP6IwGIo7K7cofWm-CFInLDd63c68");

        //String s="https://picsum.photos/200";
       // Glide.with(this).load(R.drawable.insta_img).circleCrop().into(ui_Image);

    }
}