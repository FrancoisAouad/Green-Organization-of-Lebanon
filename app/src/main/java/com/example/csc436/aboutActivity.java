package com.example.csc436;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class aboutActivity extends AppCompatActivity {

    Button about_back;
    ImageView about_insta, about_twitter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_layout);

      about_back=findViewById(R.id.backabout);
       about_insta=findViewById(R.id.insta);
       about_twitter=findViewById(R.id.twitter);



        about_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(aboutActivity.this, mainActivity.class);
                startActivity(intent);
            }
        });

        about_insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri insta = Uri.parse("https://instagram.com/green.org.leb?igshid=YmMyMTA2M2Y=");
                Intent webIntent2 = new Intent(Intent.ACTION_VIEW, insta);
                startActivity(webIntent2);
            }
        });

        about_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri twitter = Uri.parse("https://twitter.com/greenorgleb?s=21&t=cdbbP9rMhecAwcX8fowM-w");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, twitter);
                startActivity(webIntent);

            }
        });





    }

}