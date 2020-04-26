package com.example.mainactivity_homepage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignInActivity extends AppCompatActivity {

    EditText emailID, password;
    Button btnSignIN;
    TextView tvgoto_SignUP;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthSateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mFirebaseAuth = FirebaseAuth.getInstance();

        emailID = findViewById(R.id.SignIN_email);
        password = findViewById(R.id.SignIn_password);
        btnSignIN = findViewById(R.id.button_signIN);
        tvgoto_SignUP = findViewById(R.id.SignUp_goto);

        mAuthSateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(SignInActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                   // Toast.makeText(SignInActivity.this, "Please sign in", Toast.LENGTH_SHORT).show();
                }
            }
        };
        btnSignIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailID.getText().toString();
                String pwd = password.getText().toString();
                if (email.isEmpty()) {
                    emailID.setError("Please enter the E-mail!");
                    emailID.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Please enter the password");
                    password.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show(); //всплывающее окно
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignInActivity.this, "Sign up Error. Try again!!!!", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intToMain = new Intent(SignInActivity.this,MainActivity.class);
                                startActivity(intToMain);
                            }
                        }
                    });
                } else {
                    Toast.makeText(SignInActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvgoto_SignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(SignInActivity.this,RegistrationActivity.class);
                startActivity(intSignUp);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthSateListener);
    }

    }


