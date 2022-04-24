package com.example.csc436;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ticketActivity extends AppCompatActivity {


    Button ticket_back, ticket_post;
    EditText city, street, ticketB;
    ImageView img_att;
    DatabaseReference ticketRef, userRef2;
FirebaseAuth tauth;
Uri uri;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);


        ticket_back=findViewById(R.id.ticket_back);
        ticket_post=findViewById(R.id.ticket_post);
        city=findViewById(R.id.city);
        street=findViewById(R.id.street);
        ticketB=findViewById(R.id.ticket_description);
        street=findViewById(R.id.street);
        img_att=findViewById(R.id.img_att);
        tauth=FirebaseAuth.getInstance();





       //--------------back button-----------------//
        ticket_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ticketActivity.this, mainActivity.class);
                startActivity(intent);
            }
        });
        //----------------choose photo from gallery----------------//
        img_att.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);


            }
        });

        //------------upload to databse----------------//


        ticket_post.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {





               String c_user=tauth.getCurrentUser().getUid();
               ticketRef = FirebaseDatabase.getInstance().getReference().child("tickets").child(c_user);
               userRef2 = FirebaseDatabase.getInstance().getReference().child("users").child(c_user);
               final String city1=city.getText().toString().trim();
               final String street1=street.getText().toString().trim();
               final String ticket1=ticketB.getText().toString().trim();








               userRef2.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull  DataSnapshot snapshot) {



                       if(snapshot.exists()){



                           String full_name2 = snapshot.child("fullname").getValue().toString();
                           String user_name2 = snapshot.child("username").getValue().toString();
                           //String profile_p2 = snapshot.child("profileimage").getValue().toString();
                           String phone_number2=snapshot.child("phone").getValue().toString();


                           HashMap tickets=new HashMap();

                           tickets.put("id", c_user);
                           tickets.put("fullname", full_name2);
                           tickets.put("username", user_name2);
                           tickets.put("phone", phone_number2);
                           tickets.put("city", city1);
                           tickets.put("street", street1);
                           tickets.put("description", ticket1);
                           //tickets.put("attachement", profile_p2);

                           ticketRef.child("tickets").push().setValue(tickets).addOnCompleteListener(task ->
                           {
                               if (task.isSuccessful()) {

                              //     Intent intent = new Intent(ticketActivity.this, mainActivity.class);
                                //   startActivity(intent);

                                   Toast.makeText(ticketActivity.this, "Thank you for making a difference!", Toast.LENGTH_SHORT).show();


                               } else {

                                   Toast.makeText(ticketActivity.this, "error", Toast.LENGTH_SHORT).show();
                               }
                               finish();

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
                                                   newTicketMap.put("attachement", imageUrl1);

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

                               Intent intent = new Intent(ticketActivity.this, mainActivity.class);
                               startActivity(intent);
                               finish();
                           }









                       }
                   }















                   @Override
                   public void onCancelled(@NonNull  DatabaseError error) {

                   }
               });

           }
       });



}




    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            img_att.setImageURI(uri);


    }
}

                }