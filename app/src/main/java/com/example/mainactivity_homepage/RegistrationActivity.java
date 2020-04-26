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


public class RegistrationActivity extends AppCompatActivity {

    EditText userLogin, emailID, password;
    Button btnSignUP;
    TextView tvgoto_SignIN;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mFirebaseAuth = FirebaseAuth.getInstance();

        userLogin = findViewById(R.id.name_SignUP);
        emailID = findViewById(R.id.email_SignUP);
        password = findViewById(R.id.password_SignUP);
        btnSignUP = findViewById(R.id.button_signUP);
        tvgoto_SignIN = findViewById(R.id.text_goto_SignIN);

        btnSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userLog = userLogin.getText().toString();
                String email = emailID.getText().toString();
                String pwd = password.getText().toString();

                if (userLog.isEmpty()) {
                    userLogin.setError("Please enter the username!");
                    userLogin.requestFocus();
                }
                if (email.isEmpty()) {
                    emailID.setError("Please enter the E-mail!");
                    emailID.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Please enter the password");
                    password.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty() && userLog.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show(); //всплывающее окно
                } else if (!(email.isEmpty() && pwd.isEmpty() && userLog.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {

                                Toast.makeText(RegistrationActivity.this, "Incorrect e-mail or password", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegistrationActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvgoto_SignIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrationActivity.this, SignInActivity.class);
                startActivity(i);

            }
        });
    }
}
