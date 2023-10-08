package com.example.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class signUpActivity extends AppCompatActivity implements View.OnClickListener {





    private EditText emailText , passwordText ;
    private Button signUpButton ;
    private TextView signInText ;
    private ProgressBar signUpPgBar ;
    private FirebaseAuth mAuth ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.setTitle("SignUp Activity");

        emailText=findViewById(R.id.signUpEmailId) ;
        passwordText = findViewById(R.id.signUpPasswordId) ;
        signUpButton = findViewById(R.id.signUpButton) ;
        signInText = findViewById(R.id.signInText) ;
        signUpPgBar = findViewById(R.id.signUpProgressBar);


        mAuth = FirebaseAuth.getInstance() ;

        signUpButton.setOnClickListener(this);
        signInText.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.signUpButton){
            userRegister() ;
        }
        if(v.getId()==R.id.signInText){
            startActivity(new Intent(getApplicationContext(),MainActivity.class)) ;
        }

    }

    private void userRegister() {
        String email = emailText.getText().toString().trim() ;
        String pass  = passwordText.getText().toString().trim() ;
        if(email.isEmpty())
        {
            emailText.setError("Enter an email address");
            emailText.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailText.setError("Enter a valid email address");
            emailText.requestFocus();
            return;
        }
        if(pass.isEmpty())
        {
            passwordText.setError("Enter your password");
            passwordText.requestFocus();
            return;
        }

        if(pass.length()<6)
        {
            passwordText.setError("Enter a valid password");
            passwordText.requestFocus();
            return;
        }
        signUpPgBar.setVisibility(View.VISIBLE);  //You can set this line anywhere in the userRegister() function, but before onComplete method
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                signUpPgBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    finish() ;
                    Toast.makeText(getApplicationContext(), "Registry is successful", Toast.LENGTH_SHORT).show() ;
                    startActivity(new Intent(getApplicationContext(),ProfileActivity.class)) ;


                }
                else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(), "User is Already Registered", Toast.LENGTH_SHORT).show() ;
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Error" +task.getException().getMessage(), Toast.LENGTH_SHORT).show() ;
                        //this line shows the user if any error ,then what is that particular error
                    }

                }
            }
        }) ;




    }
}