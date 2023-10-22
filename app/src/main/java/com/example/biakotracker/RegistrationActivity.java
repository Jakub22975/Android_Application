package com.example.biakotracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
    private EditText email, password;
    private Button registrationBtn;
    private TextView registrationQn;

    private FirebaseAuth mAuth;
    private ProgressDialog processDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registrationBtn = findViewById(R.id.registerBtn);
        registrationQn = findViewById(R.id.registerQn);

        mAuth = FirebaseAuth.getInstance();
        processDialog = new ProgressDialog(this);


        registrationQn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        registrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();

                if (TextUtils.isEmpty(emailString)){
                    email.setError("Email is reguired");
                }
                if (TextUtils.isEmpty(passwordString)){
                    password.setError("Password is required");
                }

                else {
                    processDialog.setMessage("registration in progress");
                    processDialog.setCanceledOnTouchOutside(false);
                    processDialog.show();

                    mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                processDialog.dismiss();
                            }else {
                                Toast.makeText(RegistrationActivity.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                                processDialog.dismiss();
                            }
                        }
                    });
                }
            }
        });
    }
}