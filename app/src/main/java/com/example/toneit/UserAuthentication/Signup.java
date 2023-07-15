package com.example.toneit.UserAuthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toneit.R;
import com.example.toneit.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    public TextView signIn, userName, email, password, repeatPassword;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    CheckBox checkBox;
    public Button signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findView();
        OnclickSignInSignUpMethod();


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating your account");
        progressDialog.setMessage("we are creating your account");

        signUp.setOnClickListener(new View.OnClickListener() {
            String currentPassword;

            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) {
                    if (password.getText().toString().equals(repeatPassword.getText().toString())) {
                        currentPassword = password.getText().toString();
                        progressDialog.show();
                        mAuth = FirebaseAuth.getInstance();
                        mAuth.createUserWithEmailAndPassword(email.getText().toString(), currentPassword)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Users users = new Users(userName.getText().toString(), email.getText().toString(),
                                            password.getText().toString());
                                    String id = task.getResult().getUser().getUid();
                                    database.getInstance().getReference().child("Users").child(id).setValue(users);
                                    startActivity(new Intent(getBaseContext(), Login.class));
                                    Toast.makeText(Signup.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Signup.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getBaseContext(), "password did not match", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Signup.this, "you must accept terms and conditions to signup", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void findView() {

        signIn = findViewById(R.id.signIn);
        userName = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        checkBox = findViewById(R.id.checkBox);
        repeatPassword = findViewById(R.id.repeatPassword);
        signUp = findViewById(R.id.button);
    }

    public void OnclickSignInSignUpMethod() {

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup.this, Login.class));
            }
        });

    }


}