package com.example.csc436;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class writeActivity extends AppCompatActivity {

    //  Toolbar tool_bar;
    Button write_button, write_back;
    EditText write_text;
    DatabaseReference userRef, postRef;
    FirebaseAuth mauth;
    String savecurrentdate, savecurrenttime;

    //  String current_user = mauth.getCurrentUser().getUid();

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
            //String pfp = snapshot.child("profileimage").getValue().toString();


            HashMap newposts = new HashMap();

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


    }

}





