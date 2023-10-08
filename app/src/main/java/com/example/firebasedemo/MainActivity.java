package com.example.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {





    private EditText emailText , passwordText ;
    private Button signInButton ;
    private TextView signUpText ;
    private ProgressBar signInpgBar ;
    private FirebaseAuth mAuth  ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("SignIn Activity");

        emailText=findViewById(R.id.signInEmailId) ;
        passwordText=findViewById(R.id.signInPasswordId) ;
        signInButton=findViewById(R.id.signInButton) ;
        signUpText=findViewById(R.id.signUpText) ;
        signInpgBar = findViewById(R.id.signInProgressBar) ;

        mAuth = FirebaseAuth.getInstance() ;

        signInButton.setOnClickListener(this);
        signUpText.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.signInButton){
            userLogIn() ;

        }
       if(v.getId()==R.id.signUpText){
            startActivity(new Intent(getApplicationContext(),signUpActivity.class)) ;
        }

    }

    private void userLogIn() {
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
        signInpgBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                signInpgBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    finish() ; //finish this sign in task
                    Toast.makeText(getApplicationContext(), "LogIn successful", Toast.LENGTH_SHORT).show() ;
                    startActivity(new Intent(getApplicationContext(),ProfileActivity.class)) ;
                }
                else {
                    Toast.makeText(getApplicationContext(),"LogIn Unsuccessful",Toast.LENGTH_SHORT) ;
                }

            }
        });

    }


}