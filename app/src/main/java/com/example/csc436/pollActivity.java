package com.example.csc436;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class pollActivity extends AppCompatActivity {

    CheckBox c1, c2, c3, c4, c5;
    Button vote, back_poll;
    DatabaseReference qref, userRef, voteref;
    TextView option1, option2, option3, option4, option5;
FirebaseAuth uau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);

//(CheckBox)
        c1= findViewById(R.id.c1);
        c2=findViewById(R.id.c2);
        c3=findViewById(R.id.c3);
        c4=findViewById(R.id.c4);
        c5=findViewById(R.id.c5);
        uau=FirebaseAuth.getInstance();
back_poll=findViewById(R.id.back_poll);
        vote=findViewById(R.id.vote);
        option1=findViewById(R.id.option1);
        option2=findViewById(R.id.option2);
        option3=findViewById(R.id.option3);
        option4=findViewById(R.id.option4);
        option5=findViewById(R.id.option5);
        qref= FirebaseDatabase.getInstance().getReference().child("pollquestions");
     //   userRef= FirebaseDatabase.getInstance().getReference().child("users");
        voteref= FirebaseDatabase.getInstance().getReference();




vote.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        userRef= FirebaseDatabase.getInstance().getReference().child("users");
   userRef.addValueEventListener(new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
          if(snapshot.exists()){

          // String full_name3 = snapshot.child("fullname").getValue().toString();
           //String user_name3 = snapshot.child("username").getValue().toString();
           //String profile_p3 = snapshot.child("profileimage").getValue().toString();
          // String phone_number3=snapshot.child("phone").getValue().toString();
           //String email3=snapshot.child("email").getValue().toString();
           String currruser=uau.getCurrentUser().getUid();

           String soption1=" ";
           String soption2=" ";
           String soption3=" ";
           String soption4=" ";
           String soption5=" ";

           if(c1.isChecked()){
               soption1 ="checked";

           }else if(!c1.isChecked()){
               soption1 ="not checked";
           }


           if(c2.isChecked()){
               //soption2=soption2+"option2";
                soption2="checked";
           }else if(!c2.isChecked()){
               soption2="not checked";
           }


           if(c3.isChecked()){
               //soption3=soption3+"option3";
               soption3="checked";
           }else if(!c3.isChecked()){
               soption3="not checked";
           }


           if(c4.isChecked()){
               //soption4=soption4+"option4";
               soption4="checked";
           }else if(!c4.isChecked()){
                soption4="not checked";
           }


           if(c5.isChecked()){
               // soption5=soption5+"option5";
                soption5="checked";

           }else if(!c5.isChecked()){
                soption5="not checked";
           }



           HashMap votesmap=new HashMap();

          // votesmap.put("fullname",full_name3 );
          // votesmap.put("fullname",user_name3 );
          // votesmap.put("fullname",phone_number3 );
          // votesmap.put("fullname",email3);
           votesmap.put("id",currruser );

          votesmap.put("options1", soption1);
           votesmap.put("options2", soption2 );
           votesmap.put("options3",soption3 );
           votesmap.put("options4",soption4);
           votesmap.put("options5",soption5 );

voteref.child("votes").push().setValue(votesmap).addOnCompleteListener(task -> {


    if (task.isSuccessful()) {


        Toast.makeText(pollActivity.this, "Thank you for sharing you thoughts with us!", Toast.LENGTH_SHORT).show();


    } else {

        Toast.makeText(pollActivity.this, "error", Toast.LENGTH_SHORT).show();
    }



           });

       }

















    }

















    @Override
    public void onCancelled(@NonNull @NotNull DatabaseError error) {

    }
});




















    }
});









        back_poll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pollActivity.this, mainActivity.class);
                startActivity(intent);
            }
        });





       qref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    option1.setText(snapshot.child("option1").getValue().toString());
                    option2.setText(snapshot.child("option2").getValue().toString());
                    option3.setText(snapshot.child("option3").getValue().toString());
                    option4.setText(snapshot.child("option4").getValue().toString());
                    option5.setText(snapshot.child("option5").getValue().toString());

                }





            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });






    }
}