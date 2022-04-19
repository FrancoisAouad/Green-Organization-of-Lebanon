package com.example.csc436;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {
    Button login_register, login_button;
    TextView login_email, login_password;
    ProgressDialog loader1;
    FirebaseAuth uAuth;
    FirebaseAuth.AuthStateListener authStateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        uAuth=FirebaseAuth.getInstance();

        authStateListener =new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull  FirebaseAuth firebaseAuth) {
                FirebaseUser user =uAuth.getCurrentUser();
                if(user!= null){

                    Toast.makeText(loginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(loginActivity.this, mainActivity.class);
                    startActivity(intent);
                    finish();


                }


            }
        };


        login_register=findViewById(R.id.login_register);
        login_button=findViewById(R.id.login_button);
        login_email=findViewById(R.id.login_email);
        login_password=findViewById(R.id.login_password);
        loader1=new ProgressDialog(this);








//----------go to registration screen--------------//
        login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(loginActivity.this, registerActivity.class);
                startActivity(intent);
            }
        });

//----------------------login to main activity-----------------//
login_button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        final String email1=login_email.getText().toString().trim();
        final String password1=login_password.getText().toString().trim();

        if(TextUtils.isEmpty(email1)){
            login_email.setError("Email is Required");
            return;
        }
        if(TextUtils.isEmpty(password1)){
            login_password.setError("Password is Required");
            return;
        }

        if(password1.length()<6){
            login_password.setError("Password should be 6 or more characters");
            return;
        }
else{
    loader1.setMessage("Logging in");
    loader1.setCanceledOnTouchOutside(false);
    loader1.show();

    uAuth.signInWithEmailAndPassword(email1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull  Task<AuthResult> task) {
           if(task.isSuccessful()) {

               Toast.makeText(loginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(loginActivity.this, mainActivity.class);
               startActivity(intent);
               finish();


           }else{
               Toast.makeText(loginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
           }
           loader1.dismiss();
        }
    });

        }
    }
});



    }

    @Override
    protected void onStart() {
        super.onStart();

        uAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        uAuth.removeAuthStateListener(authStateListener);

    }
}