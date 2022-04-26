package com.example.csc436;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class myPostsActivity extends AppCompatActivity {

Button myposts_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
myposts_back=findViewById(R.id.myposts_back);

        myposts_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myPostsActivity.this, mainActivity.class);
                startActivity(intent);
            }
        });




    }
}