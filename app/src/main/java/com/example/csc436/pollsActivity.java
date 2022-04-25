package com.example.csc436;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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



import java.util.HashMap;

public class pollsActivity extends AppCompatActivity {
    RadioGroup radioGroup2, radioGroup3, radioGroup4, radioGroup5;
    RadioButton polls1, polls2, polls3, polls4, polls5, polls6, polls7, polls8, polls9, polls10, polls11, polls12;
    TextView qu1, qu2, qu3, qu4;
    Button pollsBack, send_poll;
    FirebaseAuth uauh;
    DatabaseReference uref, pollref, questref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polls);
        pollref = FirebaseDatabase.getInstance().getReference().child("pollanswers");
        //   userRef= FirebaseDatabase.getInstance().getReference().child("users");
        uref = FirebaseDatabase.getInstance().getReference().child("users");
        questref = FirebaseDatabase.getInstance().getReference().child("pollquestions");

        pollsBack = findViewById(R.id.pollsBack);
        send_poll = findViewById(R.id.send_poll);
        qu1 = findViewById(R.id.qu1);
        qu2 = findViewById(R.id.qu2);
        qu3 = findViewById(R.id.qu3);
        qu4 = findViewById(R.id.qu4);

        radioGroup2 = findViewById(R.id.radiogroup2);
        radioGroup3 = findViewById(R.id.radiogroup3);
        radioGroup4 = findViewById(R.id.radiogroup4);
        radioGroup5 = findViewById(R.id.radiogroup5);


        polls1 = findViewById(R.id.polls1);
        polls2 = findViewById(R.id.polls2);
        polls3 = findViewById(R.id.polls3);
        polls4 = findViewById(R.id.polls4);
        polls5 = findViewById(R.id.polls5);
        polls6 = findViewById(R.id.polls6);
        polls7 = findViewById(R.id.polls7);
        polls8 = findViewById(R.id.polls8);
        polls9 = findViewById(R.id.polls9);
        polls10 = findViewById(R.id.polls10);
        polls11 = findViewById(R.id.polls11);
        polls12 = findViewById(R.id.polls12);
        uauh = FirebaseAuth.getInstance();
//----------------back button
        pollsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pollsActivity.this, mainActivity.class);
                startActivity(intent);
            }
        });

//-----------------vote button

        send_poll.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {


                if (radioGroup2.getCheckedRadioButtonId() <= 0) {//Grp is your radio group object
                    polls3.setError("Select Item");//Set error to last Radio button

                }
                if (radioGroup3.getCheckedRadioButtonId() <= 0) {//Grp is your radio group object
                    polls6.setError("Select Item");//Set error to last Radio button
                }
                if (radioGroup4.getCheckedRadioButtonId() <= 0) {//Grp is your radio group object
                    polls9.setError("Select Item");//Set error to last Radio button
                }
                if (radioGroup5.getCheckedRadioButtonId() <= 0) {//Grp is your radio group object
                    polls12.setError("Select Item");//Set error to last Radio button
                } else {



                uref = FirebaseDatabase.getInstance().getReference().child("users");
                uref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                        if (snapshot.exists()) {

                            String currruserID = uauh.getCurrentUser().getUid();


//------------------radio buttons
                            String spolls1 = " ";
                            String spolls2 = " ";
                            String spolls3 = " ";
                            String spolls4 = " ";
                            String spolls5 = " ";
                            String spolls6 = " ";
                            String spolls7 = " ";
                            String spolls8 = " ";
                            String spolls9 = " ";
                            String spolls10 = " ";
                            String spolls11 = " ";
                            String spolls12 = " ";

                            if (polls1.isChecked()) {
                                spolls1 = "selected";

                            } else if (!polls1.isChecked()) {
                                spolls1 = "not selected";
                            }


                            if (polls2.isChecked()) {
                                spolls2 = "selected";

                            } else if (!polls2.isChecked()) {
                                spolls2 = "not selected";
                            }


                            if (polls3.isChecked()) {
                                spolls3 = "selected";

                            } else if (!polls3.isChecked()) {
                                spolls3 = "not selected";
                            }


                            if (polls4.isChecked()) {
                                spolls4 = "selected";

                            } else if (!polls4.isChecked()) {
                                spolls4 = "not selected";
                            }


                            if (polls5.isChecked()) {
                                spolls5 = "selected";

                            } else if (!polls5.isChecked()) {
                                spolls5 = "not selected";
                            }


                            if (polls6.isChecked()) {
                                spolls6 = "selected";

                            } else if (!polls6.isChecked()) {
                                spolls6 = "not selected";
                            }

                            if (polls7.isChecked()) {
                                spolls7 = "selected";

                            } else if (!polls7.isChecked()) {
                                spolls7 = "not selected";
                            }


                            if (polls8.isChecked()) {
                                spolls8 = "selected";

                            } else if (!polls8.isChecked()) {
                                spolls8 = "not selected";
                            }


                            if (polls9.isChecked()) {
                                spolls9 = "selected";

                            } else if (!polls9.isChecked()) {
                                spolls9 = "not selected";
                            }


                            if (polls10.isChecked()) {
                                spolls10 = "selected";

                            } else if (!polls10.isChecked()) {
                                spolls10 = "not selected";
                            }


                            if (polls11.isChecked()) {
                                spolls11 = "selected";

                            } else if (!polls11.isChecked()) {
                                spolls11 = "not selected";
                            }


                            if (polls12.isChecked()) {
                                spolls12 = "selected";

                            } else if (!polls12.isChecked()) {
                                spolls12 = "not selected";
                            }

                            HashMap NpollsMap = new HashMap();

                            // votesmap.put("fullname",full_name3 );
                            // votesmap.put("fullname",user_name3 );
                            // votesmap.put("fullname",phone_number3 );
                            // votesmap.put("fullname",email3);
                            NpollsMap.put("id", currruserID);


                            NpollsMap.put("pollR1", spolls1);
                            NpollsMap.put("pollR2", spolls2);
                            NpollsMap.put("pollR3", spolls3);
                            NpollsMap.put("pollR4", spolls4);
                            NpollsMap.put("pollR5", spolls5);
                            NpollsMap.put("pollR6", spolls6);
                            NpollsMap.put("pollR7", spolls7);
                            NpollsMap.put("pollR8", spolls8);
                            NpollsMap.put("pollR9", spolls9);
                            NpollsMap.put("pollR10", spolls10);
                            NpollsMap.put("pollR11", spolls11);
                            NpollsMap.put("pollR12", spolls12);


                            pollref.child("pollanswers").push().setValue(NpollsMap).addOnCompleteListener(task -> {


                                if (task.isSuccessful()) {


                                    Toast.makeText(pollsActivity.this, "Thank you for voting!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(pollsActivity.this, mainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {

                                    Toast.makeText(pollsActivity.this, "error", Toast.LENGTH_SHORT).show();
                                }

                            });

                        }
                    }


                    @Override
                    public void onCancelled(@NonNull  DatabaseError error) {

                    }
                });
            }

            }
        });










     //------------display from database
questref.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull  DataSnapshot snapshot) {
        if(snapshot.exists()){


            qu1.setText(snapshot.child("pollQ1").getValue().toString());
            qu2.setText(snapshot.child("pollQ2").getValue().toString());
            qu3.setText(snapshot.child("pollQ3").getValue().toString());
            qu4.setText(snapshot.child("pollQ4").getValue().toString());
          //  option5.setText(snapshot.child("options5").getValue().toString());

            polls1.setText(snapshot.child("R1").getValue().toString());
            polls2.setText(snapshot.child("R2").getValue().toString());
            polls3.setText(snapshot.child("R3").getValue().toString());
            polls4.setText(snapshot.child("R4").getValue().toString());
            polls5.setText(snapshot.child("R5").getValue().toString());
            polls6.setText(snapshot.child("R6").getValue().toString());
            polls7.setText(snapshot.child("R7").getValue().toString());
            polls8.setText(snapshot.child("R8").getValue().toString());
            polls9.setText(snapshot.child("R9").getValue().toString());
            polls10.setText(snapshot.child("R10").getValue().toString());
            polls11.setText(snapshot.child("R11").getValue().toString());
            polls12.setText(snapshot.child("R12").getValue().toString());
        }


    }

    @Override
    public void onCancelled(@NonNull  DatabaseError error) {

    }
});












    }

}