package com.example.csc436;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    TextView rights, title;
    ImageView logo;

    Animation logoanimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    rights=findViewById(R.id.rights);
        title=findViewById(R.id.title);
        logo=findViewById(R.id.logo);

        logoanimation= AnimationUtils.loadAnimation(this, R.anim.logo_animation);


    logo.setAnimation(logoanimation);

    int SPLASH_SCREEN=4300;

    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            Intent intent=new Intent(SplashActivity.this, loginActivity.class);
            startActivity(intent);
            finish();
        }
    }, SPLASH_SCREEN);


    }
}