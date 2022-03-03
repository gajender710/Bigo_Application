package com.example.bigo_application;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Home extends AppCompatActivity {

    private static final String TAG = "Home";

    private static final int IMG_REQUEST_ID = 1;
    private URI uri;
    FirebaseFirestore fstore;
    TextView textView;
    SimpleDraweeView image;
    //ImageView image;
    private Button uploadBtn, saveBtn;
    String user_id;
    FirebaseStorage fireStg;
    StorageReference storageReference;
    public File tempFile;
    public Uri path;

    private final ActivityResultLauncher<String>
            someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), result -> {
                if (result) {
                    Log.d(TAG, "permission granted: ");
                } else {
                    Log.e(TAG, "permission denied: ");
                }
            }
    );

    public final ActivityResultLauncher<String>
            someActivity = registerForActivityResult(
            new ActivityResultContracts.GetContent(), result -> {
                if (result != null && result.getPath() != null) {
                    Log.d(TAG, "registerForActivityResult "+result.getPath());
                    //Picasso.get().load(result.toString()).resize(200,200).centerCrop().into(image);
                    path=result;
                    choosePicture();
                   // tempFile=new File(Environment.getExternalStorageDirectory().getPath()+result.toString());

                } else {
                    Log.e(TAG, "registerForActivityResult: ");
                }
            }
    );

    private final ActivityResultLauncher<String[]>
            someActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(), results -> {
                String[] keys = results.keySet().toArray(new String[4]);
                for (int i = 0; i < results.keySet().size(); i++) {
                    switch (keys[i]) {
                        case Manifest.permission.READ_EXTERNAL_STORAGE:
                        case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                        case Manifest.permission.CAMERA:
                        case Manifest.permission.READ_CONTACTS: {
                            Log.d(TAG, "Some activity:permissio granted ");
                            break;
                        }
                        default: {
                            Log.d(TAG, "Some activity:permissio granted ");
                            break;
                        }
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textView = findViewById(R.id.user_Space);
        image = findViewById(R.id.profile_pic);

        uploadBtn = findViewById(R.id.upload_Btn);
        saveBtn = findViewById(R.id.Save_btn);
        saveBtn.setEnabled(false);
        String[] s = new String[4];
        s[0] = Manifest.permission.READ_EXTERNAL_STORAGE;
        s[1] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        s[2] = Manifest.permission.CAMERA;
        s[3] = Manifest.permission.READ_CONTACTS;


        fireStg = FirebaseStorage.getInstance();
        storageReference = fireStg.getReference(randon_names());

        user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        fstore = FirebaseFirestore.getInstance();
        DocumentReference doc_ref = fstore.collection("UserData").document(user_id);
        doc_ref.addSnapshotListener(this, (value, error) -> {
            textView.setText(value.getString("Email"));
            Picasso.get().load(value.getString("URL")).resize(200,200).centerCrop().into(image);
        });



        findViewById(R.id.log_out_btn).setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(Home.this, Sign_In.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        /*uploadBtn.setOnClickListener(view -> {
            someActivityResultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        });
        */

        uploadBtn.setOnClickListener(view -> {
            someActivity.launch("image/*");

        });

        saveBtn.setOnClickListener(view -> {
            someActivityLauncher.launch(s);

        });

    }

    private String randon_names() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }

    private void choosePicture() {
        try {
           // File file = new File(Environment.getExternalStorageDirectory().getPath() + "/DCIM/Screenshots/Screenshot_2019-02-23-18-24-58-24.png");

            image.setImageURI("https://www.holdapp.com/wp-content/uploads/2021/05/saf_file_select-485x1024.png");


            //Glide.with(this).load(path.toString()).override(200,200).fitCenter();
                    //.transition(DrawableTransitionOptions.withCrossFade(2000)).into(image);
            //Picasso.get().load(path.toString()).resize(200,200).centerCrop().into(image);
            //UploadTask uppload = storageReference.putFile(Uri.parse(path.toString()));

            //InputStream stream = new FileInputStream(Environment.getExternalStorageDirectory().getPath() + "/DCIM/Screenshots/Screenshot_2019-02-23-18-24-58-24.png");
            UploadTask upload = storageReference.putFile(Uri.parse(path.toString()));;
            upload.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uri -> {
                        DocumentReference imag_doc = fstore.collection("UserData").document(user_id);
                        Map<String,Object> img_url=new HashMap<>();
                        img_url.put("URL",uri.toString());
                        imag_doc.update(img_url).addOnSuccessListener(u->{
                            Log.d(TAG, "onSuccess: image url updated");
                        });
                    });
                    saveBtn.setEnabled(true);
                    Log.d(TAG, "onSuccess: " + taskSnapshot.getUploadSessionUri().toString());
                    Toast.makeText(Home.this, "uploaded succesfull", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     

}