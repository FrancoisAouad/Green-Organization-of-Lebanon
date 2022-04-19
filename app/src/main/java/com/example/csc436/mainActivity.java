package com.example.csc436;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar tool_bar;

    TextView navname, navusername, navemail;
    ImageView nav_img;
    DatabaseReference uRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//-------------action bar--------//
        tool_bar=findViewById(R.id.toolbar);
        setSupportActionBar(tool_bar);
        getSupportActionBar().setTitle("Home");


        drawerLayout=findViewById(R.id.drawerlayout);
        navigationView=findViewById(R.id.navigationview);
//--------------------open and close drawer------------------//
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(mainActivity.this, drawerLayout, tool_bar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
      navigationView.setNavigationItemSelectedListener(this);




        navname=navigationView.getHeaderView(0).findViewById(R.id.nav_name);
        navusername=navigationView.getHeaderView(0).findViewById(R.id.nav_username);
        nav_img=navigationView.getHeaderView(0).findViewById(R.id.user_image);


//-----------------display user data in text views from firebase in navigation menu-----------------//
        uRef= FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        uRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String f_name=snapshot.child("fullname").getValue().toString();
                    navname.setText(f_name);

                    String user_name=snapshot.child("username").getValue().toString();
                    navusername.setText(user_name);

                  //  String e_mail=snapshot.child("email").getValue().toString();
                    //navemail.setText(e_mail);
                if(snapshot.hasChild("profileimage")) {
    String imageurl = snapshot.child("profileimage").getValue().toString();
    Glide.with(getApplicationContext()).load(imageurl).fitCenter().centerCrop().into(nav_img);

}else{
//set default user image if no picture uploaded
    nav_img.setImageResource(R.drawable.default_userimg);
}
                }


            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Log.d("TAG", error.getMessage()); //display errors
            }
        });



    }
//---------------------navigate from and out of the menu items activities------------//
   @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.profile:
                Intent intent=new Intent(mainActivity.this, profileActivity.class);
                startActivity(intent);
                  break;
            case R.id.ticket:
                Intent intent2=new Intent(mainActivity.this, ticketActivity.class);
                startActivity(intent2);
                break;
            case R.id.aboutus:
                Intent intent3=new Intent(mainActivity.this, aboutActivity.class);
                startActivity(intent3);
                break;
            case R.id.newposts:
                Intent intent4=new Intent(mainActivity.this, writeActivity.class);
                startActivity(intent4);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), loginActivity.class));
                finish();
                break;



        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}