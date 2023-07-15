package com.example.toneit.UserAuthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toneit.Post.PostModel;
import com.example.toneit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    Button signIn;
    TextView singUp;
    TextView email,password;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findView();

        
        OnclickSignInSignUpMethod();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating your account");
        progressDialog.setMessage("Log into your account");

        signInMethod();
    }

    public void findView() {
        singUp = findViewById(R.id.signUp);
        signIn = findViewById(R.id.button);
        email = findViewById(R.id.userName);
        password = findViewById(R.id.password);
    }

    public void OnclickSignInSignUpMethod() {

        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Signup.class));
            }
        });


    }
    public void signInMethod(){
        mAuth=FirebaseAuth.getInstance();

            signIn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    if (email.getText().toString().equals("") && password.getText().toString().equals("")) {

                        Toast.makeText(getBaseContext(), "please  enter valid username and password", Toast.LENGTH_SHORT).show();

                    } else if(email.getText().toString().equals("")){
                        Toast.makeText(getBaseContext(), "please  enter valid username.", Toast.LENGTH_SHORT).show();
                    }else if(password.getText().toString().equals("")){
                        Toast.makeText(getBaseContext(), "please  enter valid password", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        progressDialog.show();
                        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(getBaseContext(), PostModel.HomeActivity.class));
                                    Toast.makeText(Login.this, "successfully login", Toast.LENGTH_SHORT).show();
                                } else {

                                    Toast.makeText(Login.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }
                }
            });



if(mAuth.getCurrentUser()!=null){

    startActivity(new Intent(getBaseContext(), PostModel.HomeActivity.class));
}


    }

}