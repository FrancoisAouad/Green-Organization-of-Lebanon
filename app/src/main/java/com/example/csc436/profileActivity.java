package com.example.csc436;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profileActivity extends AppCompatActivity {
     ImageView profile_image;
     TextView profile_fullname, profile_username, profile_email, profile_gender, profile_phone;
    // Toolbar tool_bar;
     FirebaseAuth uAuth;
     DatabaseReference dataref;
     Button profile_back;
     String update_username, update_email, update_number, update_password;



     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        uAuth=FirebaseAuth.getInstance();
        profile_image=findViewById(R.id.profile_image);
        profile_fullname=findViewById(R.id.profile_full_name);
        profile_username=findViewById(R.id.profile_username);
        profile_email=findViewById(R.id.profile_email);
       profile_back=findViewById(R.id.profile_back);
        profile_phone=findViewById(R.id.profile_phone);
       // tool_bar=findViewById(R.id.toolbar);

        dataref=FirebaseDatabase.getInstance().getReference("users");
       //---------------action bar setup-------------------//

       // setSupportActionBar(tool_bar);
       // getSupportActionBar().setTitle("Profile");
      // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  getSupportActionBar().setDisplayShowHomeEnabled(true);


         profile_back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(profileActivity.this, mainActivity.class);
                 startActivity(intent);
             }
         });






//----------------display info in profile activity

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
if(snapshot.exists()){

    profile_fullname.setText(snapshot.child("fullname").getValue().toString());
    profile_username.setText(snapshot.child("username").getValue().toString());
    profile_email.setText(snapshot.child("email").getValue().toString());
   // profile_gender.setText(snapshot.child("gender").getValue().toString());
    profile_phone.setText(snapshot.child("phone").getValue().toString());
    if(snapshot.hasChild("profileimage")) {

        Glide.with(getApplicationContext()).load(snapshot.child("profileimage").getValue().toString()).into(profile_image);
    }else{

        profile_image.setImageResource(R.drawable.default_userimg);
    }
}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", error.getMessage()); //Don't ignore potential errors!
            }
        });


     /*  profile_update.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

              
if(isPhoneChanged()|| isUserNameChanged() ){

    Toast.makeText(profileActivity.this, "Info successfully changed", Toast.LENGTH_SHORT).show();

}
else{

    Toast.makeText(profileActivity.this, "Error", Toast.LENGTH_SHORT).show();
}





           }
       });*/










    }

    private boolean isPhoneChanged() {
        if(!update_number.equals(profile_phone.getText().toString())){

            dataref.child(update_number).child("phone").setValue(profile_phone.getText().toString());
            update_number=profile_phone.getText().toString();
            return true;
        }else{
            return false;
        }

    }

    private boolean isUserNameChanged() {

        if(!update_username.equals(profile_username.getText().toString())){

            dataref.child(update_username).child("username").setValue(profile_username.getText().toString());
            update_username=profile_username.getText().toString();
            return true;
        }else{
            return false;
        }
    }






    //-----------------back button in toolbar------------------//
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }


}