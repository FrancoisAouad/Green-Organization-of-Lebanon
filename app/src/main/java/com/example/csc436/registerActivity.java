package com.example.csc436;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class registerActivity extends AppCompatActivity {

    Button back_button, register_button;
    EditText register_name, register_username, register_email, register_password, register_phone;
    Spinner register_gender;
    ProgressDialog loader;
    FirebaseAuth uAuth;
    DatabaseReference dataRef;
    ImageView register_userimg;
    Uri resulturi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        back_button = findViewById(R.id.back_button);
        register_button = findViewById(R.id.register_button);
        register_userimg = findViewById(R.id.register_userimg);
        register_phone = findViewById(R.id.register_phone);
        register_name = findViewById(R.id.register_name);
        register_username = findViewById(R.id.register_username);
        register_email = findViewById(R.id.register_email);
        register_password = findViewById(R.id.register_password);
        register_gender = findViewById(R.id.register_gender);
        loader = new ProgressDialog(this);
        uAuth = FirebaseAuth.getInstance();


//--------------------Back button-----------------------------//

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registerActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
        //--------------//
      register_userimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);


            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = register_email.getText().toString().trim();
                final String password = register_password.getText().toString().trim();
                final String fullname = register_name.getText().toString().trim();
                final String username = register_username.getText().toString().trim();
                final String gender = register_gender.getSelectedItem().toString();
                final String phone = register_phone.getText().toString().trim();
//-----------------------send info to firebase-----------//
                if (TextUtils.isEmpty(email)) {
                    register_email.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    register_password.setError("Password is Required");
                    return;
                }

                if (TextUtils.isEmpty(fullname)) {
                    register_name.setError("Full Name is Required");
                    return;
                }

                if (TextUtils.isEmpty(username)) {
                    register_username.setError("Username Name is Required");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    register_phone.setError("Phone number is Required");
                    return;
                }
                if (password.length() < 6) {
                    register_password.setError("Password should be 6 or more characters");
                    return;
                }

                if (gender.equals("Select your Gender")) {
                    Toast.makeText(registerActivity.this, "Select Gender", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    loader.setMessage("This may take a second...");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
//------------------create user-----------------//
                    uAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                String error = task.getException().toString();
                                Toast.makeText(registerActivity.this, "Error" + error, Toast.LENGTH_SHORT).show();
                            } else {
                                String current_user_id = uAuth.getCurrentUser().getUid();
                                dataRef = FirebaseDatabase.getInstance().getReference().child("users").child(current_user_id);

                                HashMap userinfo = new HashMap();
                               // userinfo.put("id", current_user_id);
                                userinfo.put("fullname", fullname);
                                userinfo.put("username", username);
                                userinfo.put("phone", phone);
                                userinfo.put("email", email);
                                userinfo.put("gender", gender);


                                dataRef.updateChildren(userinfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {

                                        if (task.isSuccessful()) {

                                            Toast.makeText(registerActivity.this, "You have successfully become a member!", Toast.LENGTH_SHORT).show();
                                        } else {

                                            Toast.makeText(registerActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();


                                        }
                                        finish();
                                        //loader.dismiss();
                                    }
                                });


                                if (resulturi != null) {
                                    final StorageReference filepath = FirebaseStorage.getInstance().getReference().child("profileimages").child(current_user_id);
                                    Bitmap bitmap = null;

                                    try {

                                        bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resulturi);

                                    } catch (IOException e) {

                                        e.printStackTrace();

                                    }

                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                                    byte[] data = byteArrayOutputStream.toByteArray();
                                    UploadTask uploadTask = filepath.putBytes(data);

                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(registerActivity.this, "Image upload failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override


                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            if (taskSnapshot.getMetadata() != null && taskSnapshot.getMetadata().getReference() != null) {
                                                Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();

                                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        String imageUrl = uri.toString();
                                                        Map newImageMap = new HashMap();
                                                        newImageMap.put("profileimage", imageUrl);

                                                        dataRef.updateChildren(newImageMap).addOnCompleteListener(new OnCompleteListener() {
                                                            @Override
                                                            public void onComplete(@NonNull Task task) {
                                                                if (task.isSuccessful()) {

                                                                    Toast.makeText(registerActivity.this, "Image added successfully", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    Toast.makeText(registerActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();


                                                                }
                                                            }
                                                        });
                                                        finish();
                                                    }
                                                });


                                            }
                                        }
                                    });
                                    Intent intent = new Intent(registerActivity.this, mainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    loader.dismiss();
                                }

                            }

                        }

                    });
                }

            }
        });
}
    @Override
    protected void onActivityResult ( int requestCode, int resultCode,
                                      @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            resulturi = data.getData();
            register_userimg.setImageURI(resulturi);


        }

    }
}