package com.example.csc436;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class calendarActivity extends AppCompatActivity {

    TextView datec;
    ImageView calendarimg;
    Button backc;
    DatabaseReference dateRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        dateRef = FirebaseDatabase.getInstance().getReference().child("calendar");


        datec=findViewById(R.id.calendar_date);
        calendarimg=findViewById(R.id.calendarimg);
        backc=findViewById(R.id.calendar_back);

        backc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(calendarActivity.this, mainActivity.class);
                startActivity(intent);
            }
        });




dateRef.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull  DataSnapshot snapshot) {
        if(snapshot.exists()){

            if(snapshot.hasChild("calendarurl")) {

                Glide.with(getApplicationContext()).load(snapshot.child("calendarurl").getValue().toString()).into(calendarimg);
            }else{

                calendarimg.setImageResource(R.drawable.default_userimg);
            }

            datec.setText(snapshot.child("calendardate").getValue().toString());

        }




    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});




    }
}