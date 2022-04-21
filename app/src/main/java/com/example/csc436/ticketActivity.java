package com.example.csc436;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ticketActivity extends AppCompatActivity {


    Button ticket_back, ticket_post;
    EditText city, street, ticketB;
    ImageView img_att;


   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);


        ticket_back=findViewById(R.id.ticket_back);
        ticket_post=findViewById(R.id.ticket_back);
        city=findViewById(R.id.city);
        street=findViewById(R.id.street);
        ticketB=findViewById(R.id.ticket_description);
        street=findViewById(R.id.street);
        img_att=findViewById(R.id.img_att);





    }
}