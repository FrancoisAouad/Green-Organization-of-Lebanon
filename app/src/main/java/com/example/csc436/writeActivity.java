package com.example.csc436;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class writeActivity extends AppCompatActivity {

    //  Toolbar tool_bar;
    Button write_button, write_back;
    EditText write_text;
    DatabaseReference userRef, postRef;
    FirebaseAuth mauth;
    String savecurrentdate, savecurrenttime;

    //  String current_user = mauth.getCurrentUser().getUid();
//Fix an error where we cannot send a post if the user has not selected an image


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);


        write_button = findViewById(R.id.writepostbutton);
        write_text = findViewById(R.id.writeposttext);
        mauth = FirebaseAuth.getInstance();
        write_back = findViewById(R.id.writepostback);

        String current_user = mauth.getCurrentUser().getUid();


        write_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String post = write_text.getText().toString().trim();

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat currendate = new SimpleDateFormat("dd-MMMM-yyyy");
                savecurrentdate = currendate.format(calendar.getTime());

                Calendar time = Calendar.getInstance();
                SimpleDateFormat currentime = new SimpleDateFormat("HH:mm");
                savecurrenttime = currentime.format(time.getTime());

                postRef = FirebaseDatabase.getInstance().getReference().child("posts").child(current_user);
                userRef = FirebaseDatabase.getInstance().getReference().child("users").child(current_user);
                // String current_user = mauth.getCurrentUser().getUid();

userRef.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull  DataSnapshot snapshot) {
        if(snapshot.exists()){

            String full_name = snapshot.child("fullname").getValue().toString();
            String user_name = snapshot.child("username").getValue().toString();
           String profile_p = snapshot.child("profileimage").getValue().toString();


            HashMap newposts = new HashMap();

            newposts.put("id", current_user);
            newposts.put("fullname", full_name);
            newposts.put("username", user_name);
            newposts.put("date", savecurrentdate);
            newposts.put("time", savecurrenttime);
            newposts.put("text", post);
            newposts.put("profileimage", profile_p);

            postRef.child("newposts").push().setValue(newposts).addOnCompleteListener(task ->
            {


                if (task.isSuccessful()) {
                    Toast.makeText(writeActivity.this, "Post sent", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(writeActivity.this, mainActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    Toast.makeText(writeActivity.this, "error", Toast.LENGTH_SHORT).show();
                }

               /* if(snapshot.hasChild("profileimage")) {

                    Glide.with(getApplicationContext()).load(snapshot.child("profileimage").getValue().toString()).into(profile_p);
                }else{

                    profile_p.put
                }

*/

            });


        }
    }

    @Override
    public void onCancelled(@NonNull  DatabaseError error) {

    }
});


           /*     HashMap newposts = new HashMap();

                 newposts.put("id", current_user);
                 newposts.put("fullname", full_name);
                newposts.put("username", user_name);
                newposts.put("date", savecurrentdate);
                newposts.put("time", savecurrenttime);
                newposts.put("text", post);
                // newposts.put("profileimage", pfp);

                postRef.child("newposts").push().setValue(newposts).addOnCompleteListener(task ->
                {


                    if (task.isSuccessful()) {
                        Toast.makeText(writeActivity.this, "Post sent", Toast.LENGTH_SHORT).show();


                    } else {

                        Toast.makeText(writeActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }

                });*/
            }
        });

        write_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(writeActivity.this, mainActivity.class);
                startActivity(intent);
                finish();
            }
        });
//-----------------------------------------------------------------------------------------------------------------
     /*   write_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                String c_user=tauth.getCurrentUser().getUid();
                ticketRef = FirebaseDatabase.getInstance().getReference().child("tickets").child(c_user);
                userRef2 = FirebaseDatabase.getInstance().getReference().child("users").child(c_user);
                final String city1=city.getText().toString().trim();
                final String street1=street.getText().toString().trim();
                final String ticket1=ticketB.getText().toString().trim();








                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {



                        if(snapshot.exists()){



                            String full_name2 = snapshot.child("fullname").getValue().toString();
                            String user_name2 = snapshot.child("username").getValue().toString();
                            String profile_p2 = snapshot.child("profileimage").getValue().toString();
                            String phone_number2=snapshot.child("phone").getValue().toString();


                            HashMap tickets=new HashMap();

                            tickets.put("id", c_user);
                            tickets.put("fullname", full_name2);
                            tickets.put("username", user_name2);
                            tickets.put("phone", phone_number2);
                            tickets.put("city", city1);
                            tickets.put("street", street1);
                            tickets.put("description", ticket1);
                            tickets.put("attachement", profile_p2);

                            ticketRef.child("tickets").push().setValue(tickets).addOnCompleteListener(task ->
                            {
                                if (task.isSuccessful()) {


                                    Toast.makeText(ticketActivity.this, "Thank you for making a difference!", Toast.LENGTH_SHORT).show();


                                } else {

                                    Toast.makeText(ticketActivity.this, "error", Toast.LENGTH_SHORT).show();
                                }


                            });

                            if (uri != null) {
                                final StorageReference filepath = FirebaseStorage.getInstance().getReference().child("ticketsimages").child(c_user);
                                Bitmap bitmap = null;

                                try {

                                    bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), uri);

                                } catch (IOException e) {

                                    e.printStackTrace();

                                }

                                ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream1);
                                byte[] data1 = byteArrayOutputStream1.toByteArray();
                                UploadTask uploadTask1 = filepath.putBytes(data1);

                                uploadTask1.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ticketActivity.this, "Image upload failed", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                //------------------
                                uploadTask1.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override

                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        if (taskSnapshot.getMetadata() != null && taskSnapshot.getMetadata().getReference() != null) {
                                            Task<Uri> result1 = taskSnapshot.getStorage().getDownloadUrl();

                                            result1.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    String imageUrl1 = uri.toString();
                                                    Map newTicketMap = new HashMap();
                                                    newTicketMap.put("ticketsimages", imageUrl1);

                                                    ticketRef.updateChildren(newTicketMap).addOnCompleteListener(new OnCompleteListener() {
                                                        @Override
                                                        public void onComplete(@NonNull Task task) {
                                                            if (task.isSuccessful()) {

                                                                Toast.makeText(ticketActivity.this, "Image added successfully", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(ticketActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();


                                                            }
                                                        }
                                                    });

                                                }
                                            });
                                        }
                                    }
                                });
                            });









                            }
                    }















                    @Override
                    public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

                    }
                });

            }
        });


        */















    }

}





