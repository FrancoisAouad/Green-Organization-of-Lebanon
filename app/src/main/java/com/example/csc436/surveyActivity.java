package com.example.csc436;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class surveyActivity extends AppCompatActivity {


    RadioGroup radioGroup;
    CheckBox c1, c2, c3, c4, c5;
    Button vote_form, back_poll;
    DatabaseReference qref, userRef, voteref;
    TextView option1, option2, option3, option4, option5;
    FirebaseAuth uau;
    TextView q1, q2, q3, q4, q5;
    EditText t1, t2, t3;
    RadioButton radio1, radio2, radio3, radio4, radio5, radio6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);
        radioGroup=findViewById(R.id.radiogroup);
        q1=findViewById(R.id.q1);
        q2=findViewById(R.id.q2);
        q3=findViewById(R.id.q3);
        q4=findViewById(R.id.q4);
        q5=findViewById(R.id.q5);

        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        t3=findViewById(R.id.t3);

        radio1=(RadioButton) findViewById(R.id.radio1);
        radio2=findViewById(R.id.radio2);
        radio3=findViewById(R.id.radio3);
        radio4=findViewById(R.id.radio4);
        radio5=findViewById(R.id.radio5);
        radio6=findViewById(R.id.radio6);

//(CheckBox) buttons
        c1= findViewById(R.id.c1);
        c2=findViewById(R.id.c2);
        c3=findViewById(R.id.c3);
        c4=findViewById(R.id.c4);
        c5=findViewById(R.id.c5);
        uau=FirebaseAuth.getInstance();
        back_poll=findViewById(R.id.back_poll);
//checkbox text
        vote_form=findViewById(R.id.send_form);
        option1=findViewById(R.id.option1);
        option2=findViewById(R.id.option2);
        option3=findViewById(R.id.option3);
        option4=findViewById(R.id.option4);
        option5=findViewById(R.id.option5);
        qref= FirebaseDatabase.getInstance().getReference().child("surveyquestions");
     //   userRef= FirebaseDatabase.getInstance().getReference().child("users");
        voteref= FirebaseDatabase.getInstance().getReference();




vote_form.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final String ans1 = t1.getText().toString().trim();
        final String ans2 = t2.getText().toString().trim();
        final String ans3 = t3.getText().toString().trim();


        if (TextUtils.isEmpty(ans1)) {
            t1.setError("Answer is required");
            return;
        }
        if (TextUtils.isEmpty(ans2)) {
            t2.setError("Answer is required");
            return;
        }
        if (TextUtils.isEmpty(ans3)) {
            t3.setError("Answer is required");
            return;
        }




        userRef= FirebaseDatabase.getInstance().getReference().child("users");
   userRef.addValueEventListener(new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
          if(snapshot.exists()){
         //     final String ans1 = t1.getText().toString().trim();
         //     final String ans2 = t2.getText().toString().trim();
          //    final String ans3 = t3.getText().toString().trim();

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
//------------------radio buttons
              String sradio1=" ";
              String sradio2=" ";
              String sradio3=" ";
              String sradio4=" ";
              String sradio5=" ";
              String sradio6=" ";

              if(radio1.isChecked()){
                  sradio1 ="selected";

              }else if(!radio1.isChecked()){
                  sradio1 ="not selected";
              }


              if(radio2.isChecked()){
                  sradio2 ="selected";

              }else if(!radio2.isChecked()){
                  sradio2 ="not selected";
              }


              if(radio3.isChecked()){
                  sradio3 ="selected";

              }else if(!radio3.isChecked()){
                  sradio3 ="not selected";
              }


              if(radio4.isChecked()){
                  sradio4 ="selected";

              }else if(!radio4.isChecked()){
                  sradio4 ="not selected";
              }


              if(radio5.isChecked()){
                  sradio5 ="selected";

              }else if(!radio5.isChecked()){
                  sradio5 ="not selected";
              }


              if(radio6.isChecked()){
                  sradio6 ="selected";

              }else if(!radio6.isChecked()){
                  sradio6 ="not selected";
              }

           HashMap votesmap=new HashMap();

          // votesmap.put("fullname",full_name3 );
          // votesmap.put("fullname",user_name3 );
          // votesmap.put("fullname",phone_number3 );
          // votesmap.put("fullname",email3);
           votesmap.put("id",currruser );

           votesmap.put("question1",ans1 );
           votesmap.put("question2",ans2);
           votesmap.put("question3",ans3 );

           votesmap.put("options1", soption1);
           votesmap.put("options2", soption2 );
           votesmap.put("options3",soption3 );
           votesmap.put("options4",soption4);
           votesmap.put("options5",soption5 );

           votesmap.put("radio1",sradio1 );
           votesmap.put("radio2",sradio2 );
           votesmap.put("radio3",sradio3 );
           votesmap.put("radio4",sradio4 );
           votesmap.put("radio5",sradio5 );
           votesmap.put("radio6",sradio6 );



voteref.child("surveyresponses").push().setValue(votesmap).addOnCompleteListener(task -> {


    if (task.isSuccessful()) {


        Toast.makeText(surveyActivity.this, "Your form has been successfully sent!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(surveyActivity.this, mainActivity.class);
        startActivity(intent);
        finish();

    } else {

        Toast.makeText(surveyActivity.this, "error", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(surveyActivity.this, mainActivity.class);
                startActivity(intent);
            }
        });





       qref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    option1.setText(snapshot.child("options1").getValue().toString());
                    option2.setText(snapshot.child("options2").getValue().toString());
                    option3.setText(snapshot.child("options3").getValue().toString());
                    option4.setText(snapshot.child("options4").getValue().toString());
                    option5.setText(snapshot.child("options5").getValue().toString());

                    q1.setText(snapshot.child("question1").getValue().toString());
                    q2.setText(snapshot.child("question2").getValue().toString());
                    q3.setText(snapshot.child("question3").getValue().toString());
                    q4.setText(snapshot.child("question4").getValue().toString());
                    q5.setText(snapshot.child("question5").getValue().toString());
                    q5.setText(snapshot.child("question6").getValue().toString());

                    radio1.setText(snapshot.child("radio1").getValue().toString());
                    radio2.setText(snapshot.child("radio2").getValue().toString());
                    radio3.setText(snapshot.child("radio3").getValue().toString());
                    radio4.setText(snapshot.child("radio4").getValue().toString());
                    radio5.setText(snapshot.child("radio5").getValue().toString());
                    radio6.setText(snapshot.child("radio6").getValue().toString());
                }





            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });






    }
}